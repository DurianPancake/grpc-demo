package cn.durian.grpc.service;

import cn.durian.grpc.proto.MessageRequest;
import cn.durian.grpc.proto.MessageResponse;
import cn.durian.grpc.proto.MultiStreamServiceGrpc;
import io.grpc.Channel;
import io.grpc.NameResolverRegistry;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Liuluhao
 * @Date 2020/8/27
 */
@Service
@Slf4j
public class GrpcMultiStreamClientService {

    private final Channel rpcChannel;

    GrpcMultiStreamClientService() {

        rpcChannel = NettyChannelBuilder.forAddress("127.0.0.1", 2804)
                .keepAliveTime(20, TimeUnit.SECONDS)
                .keepAliveTimeout(2, TimeUnit.SECONDS)
                .keepAliveWithoutCalls(true)
                .usePlaintext()
                .idleTimeout(24, TimeUnit.HOURS)
                .nameResolverFactory(NameResolverRegistry.getDefaultRegistry().asFactory())
                .build();
    }

    /**
     * grpc>双向流方式
     *
     * @return
     */
    public Object queryByStream() throws Exception {
        Map<String, Object> resp = new HashMap<>();

        StreamObserver<MessageResponse> req = new StreamObserver<MessageResponse>() {

            @Override
            public void onNext(MessageResponse messageResponse) {
                log.info("onNext()");
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("onError()");
            }

            @Override
            public void onCompleted() {
                log.info("onCompleted()");
            }
        };

        MultiStreamServiceGrpc.MultiStreamServiceStub stud = MultiStreamServiceGrpc.newStub(rpcChannel);
        StreamObserver<MessageRequest> reqStream = stud.queryStream(req);

        for (int i = 0; i < 100; i++) {
            MessageRequest request = MessageRequest.newBuilder()
                    .setId(UUID.randomUUID().toString())
                    .setTime(System.nanoTime()).build();

            reqStream.onNext(request);
            Thread.sleep(500);
        }
        reqStream.onCompleted();
        return resp;
    }
}
