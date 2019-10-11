package com.filter.project;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClaimManagement {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext=new InitialContext();

        Queue queue=(Queue) initialContext.lookup("queue/inbound");

        try(ActiveMQConnectionFactory cf=new ActiveMQConnectionFactory();
        JMSContext jmsContext=cf.createContext())
        {
            JMSProducer producer=jmsContext.createProducer();

            JMSConsumer consumer=jmsContext.createConsumer(queue,"hospitalId=1");

            ObjectMessage objectMessage=jmsContext.createObjectMessage();
            objectMessage.setIntProperty("hospitalId",1);
            Claim claim=new Claim();
            claim.setHospitalId(345);
            claim.setDoctorName("Krishna Murthy");
            claim.setDoctorType("Skin Specialist");
            claim.setInsuranceProvider("Bajaj Alliance");
            claim.setClaimAmount(2500);
            objectMessage.setObject(claim);

            producer.send(queue,objectMessage);

            Claim receive=consumer.receiveBody(Claim.class);
            System.out.println(receive.getClaimAmount());





        }


    }
}
