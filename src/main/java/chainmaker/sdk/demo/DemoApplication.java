package chainmaker.sdk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static chainmaker.sdk.demo.InitClient.inItChainClient;

@SpringBootApplication
public class DemoApplication  {

    public static void main(String[] args) throws Exception {
        //SpringApplication.run(DemoApplication.class, args);
        inItChainClient();
        //查询链配置
        ChainConfig.getChainConfig(InitClient.chainClient);
        //创建合约
        Contract.createContract(InitClient.chainClient, InitClient.adminUser1, InitClient.adminUser2, InitClient.adminUser3, InitClient.adminUser4);
        //调用合约
        Contract.invokeContract(InitClient.chainClient);
    }
}

