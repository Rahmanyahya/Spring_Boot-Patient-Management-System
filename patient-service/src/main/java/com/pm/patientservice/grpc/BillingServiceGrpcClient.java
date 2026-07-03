package com.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:billing-services}") String host,
            @Value("${billing.service.grpc.port:9001}") int port
    ) {
        log.info("Connect Grpc service at {}:{}", host, port);

        ManagedChannel channel = ManagedChannelBuilder.
                forAddress(host, port).
                usePlaintext().
                build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patient_id, String name, String email) {
        BillingRequest req = BillingRequest.newBuilder().setPatientId(patient_id).
            setName(name).setEmail(email).build();

        BillingResponse resp = blockingStub.createBillingAccount(req);

        log.info("Recieved response from billing service via GRPC: {}", resp);

        return resp;
    }
}
