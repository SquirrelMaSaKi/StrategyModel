package com.rj.servlet;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rj.constans.MyConstants;
import com.rj.feign.MyFeign;
import com.rj.pojo.Submit;
import com.rj.service.ServiceFilter;
import com.rj.utils.RabbitChannelUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@WebServlet(name = "strategyServlet", urlPatterns = "/ss")
public class StrategyServlet extends HttpServlet {
    @Autowired
    private MyFeign myFeign;

    /**
     * 所有实现了ServiceFilter接口的类都会放入到一个map中，我们从缓存获取key的list集合，遍历即可获取过滤器实例，遍历执行方法
     */
    @Autowired
    private Map<String, ServiceFilter> serviceFilterMap;

    @Autowired
    private AmqpTemplate amqpTemplate;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        Submit submit = new Submit();
        String id = request.getParameter("clientId");
        String content = request.getParameter("content");
        Integer clientId = Integer.valueOf(id);
        submit.setClientId(clientId);
        submit.setContent(content);

        //创建通道
        ConnectionFactory connectionFactory = RabbitChannelUtil.connectionFactory();
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        List<String> filterList = myFeign.getList(MyConstants.FILTERLIST);
        for (String filterName : filterList) {
            serviceFilterMap.get(filterName).excute(submit);
        }

        //过滤之后，如果不合格，submit的内容会变化，因为我们的有过滤规则
        if (!submit.getContent().equals(content)) {
            response.getWriter().write(submit.getContent());
            return;
        }

        //从redis获取id号，并下发对应的多个网关，实现动态创建
        List<String> ids = myFeign.getList(MyConstants.GATAWAYID);
        for (String s : ids) {
            Channel channel = connection.createChannel();
            //如果有这个队列就不再创建，否则创建，duralbe是持久耐用的意思
            channel.queueDeclare(MyConstants.GATAWAYID+s, true, false, false, null);
            amqpTemplate.convertAndSend(MyConstants.GATAWAYID+s, submit);
        }
        response.getWriter().write("OK");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
