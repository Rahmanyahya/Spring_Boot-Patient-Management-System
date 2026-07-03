package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(
        BillingGrpcService.class
    );

    @Override
    public void createBillingAccount(billing.BillingRequest billingReq,
            StreamObserver<billing.BillingResponse> respObserver) {

        log.info("createBillingAccount : {}", billingReq.toString());
        
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("active")
                .build();
        
        respObserver.onNext(response);
        respObserver.onCompleted();
    }

}
