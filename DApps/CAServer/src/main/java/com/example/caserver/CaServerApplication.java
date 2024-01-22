package com.example.caserver;

import com.example.caserver.fabric.EnrollAdmin;
import com.example.caserver.fabric.RegisterUser;
import org.hyperledger.fabric.gateway.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class CaServerApplication {

    @Bean
    public Contract getContract() throws Exception {
        //enroll Admin
        EnrollAdmin.enroll();
        //register User
        RegisterUser.register();
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a config file
        Path networkConfigPath = Paths.get("/root", "fabric-samples","test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "appUser1").networkConfig(networkConfigPath).discovery(true);

        Gateway gateway = builder.connect();
        // get the blockchain network
        Network network = gateway.getNetwork("mychannel");
        //get the contract
        Contract contract = network.getContract("parking");

        return contract;
    }

    public static void main(String[] args) {
        SpringApplication.run(CaServerApplication.class, args);
    }

}
