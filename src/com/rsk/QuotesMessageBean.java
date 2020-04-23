package com.rsk;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.util.Random;

@MessageDriven(name="MessageBean",mappedName = "jms/QuoteFinder")
public class QuotesMessageBean implements MessageListener {
    private final Random random;
    private String companyName;
    public QuotesMessageBean(){
        random = new Random();
        companyName = "London Insurance";
    }

    @Resource(name = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    private  JMSContext context;

    @PostConstruct
    public void init(){context = connectionFactory.createContext();}

    @PreDestroy
    public void cleanUp(){context.close();}

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage txtMsg = (TextMessage) message;
            Destination replyTo = txtMsg.getJMSReplyTo();
            ObjectMapper mapper = new ObjectMapper();
            String body = txtMsg.getText();

            QuoteDetails quoteDetails = new QuoteDetails(companyName, random.nextInt(400));
            String userRegJson = mapper.writeValueAsString(quoteDetails);

            JMSProducer producer = context.createProducer();
            producer.send(replyTo, userRegJson);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
