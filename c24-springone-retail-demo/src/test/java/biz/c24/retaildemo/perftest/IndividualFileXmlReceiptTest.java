package biz.c24.retaildemo.perftest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;

import biz.c24.retaildemo.si.ThroughputMonitoringOutboundAdapter;

@Configuration
public class IndividualFileXmlReceiptTest {
    
    @Bean(name="persistReceipts")
    public ThroughputMonitoringOutboundAdapter getOutboundAdapter() {
    	return new ThroughputMonitoringOutboundAdapter(1000);
    }
    
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext parentContext = new AnnotationConfigApplicationContext(IndividualFileXmlReceiptTest.class);
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setParent(parentContext);
        context.load("classpath:receipts/receipt-format-xml.xml");
        context.load("classpath:receipts/receipt-transport-file.xml");
        context.refresh();
        
        
                
        SourcePollingChannelAdapter adapter = context.getBean("filePoller", SourcePollingChannelAdapter.class);
        adapter.start();

        
        Thread.sleep(15000);
        
        adapter.stop();
        context.stop();
        System.exit(0);
        
    }
    
}