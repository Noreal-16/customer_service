package org.client_person_service.client_person_service.infrastructure.grpc;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.client_person_service.client_person_service.application.interfaces.GetInfoCustomerService;
import org.client_person_service.client_person_service.grpc.CustomerRequire;
import org.client_person_service.client_person_service.grpc.CustomerResponse;
import org.client_person_service.client_person_service.grpc.CustomerServiceGrpc;

@GrpcService
@AllArgsConstructor
public class GetInfoCustomerGrpcImp extends CustomerServiceGrpc.CustomerServiceImplBase {

    private GetInfoCustomerService getInfoCustomerService;

    /**
     * client class mapping for sending with grpc
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void getInfoCustomer(CustomerRequire request, StreamObserver<CustomerResponse> responseObserver) {
        getInfoCustomerService.getInfoCustomer(Long.parseLong(request.getId()))
                .map(customerEntity -> CustomerResponse.newBuilder()
                        .setId(customerEntity.getId().toString())
                        .setName(customerEntity.getName())
                        .setGender(customerEntity.getGender())
                        .setAge(customerEntity.getAge().toString())
                        .setIdentification(customerEntity.getIdentification())
                        .setDirection(customerEntity.getDirection())
                        .setPhone(customerEntity.getPhone())
                        .setPassword(customerEntity.getPassword())
                        .setStatus(customerEntity.getStatus().toString())
                        .setPersonIdBytes(ByteString.copyFromUtf8(customerEntity.getPersonId().toString()))
                        .build())
                .doOnNext(responseObserver::onNext)
                .doOnTerminate(responseObserver::onCompleted)
                .subscribe();
    }
}
