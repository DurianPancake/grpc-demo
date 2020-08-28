package cn.durian.grpc.service;

import cn.durian.grpc.proto.MessageRequest;
import cn.durian.grpc.proto.MessageResponse;
import cn.durian.grpc.proto.MultiStreamServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author Liuluhao
 * @Date 2020/8/27
 */
@GrpcService
@Slf4j
public class GrpcMultiStreamService extends MultiStreamServiceGrpc.MultiStreamServiceImplBase {

    @Override
    public StreamObserver<MessageRequest> queryStream(StreamObserver<MessageResponse> resp) {

        return new StreamObserver<MessageRequest>() {
            @Override
            public void onNext(MessageRequest multiStreamReq) {
                log.info(String.format("耗时：%d ns", System.nanoTime() - multiStreamReq.getTime()));
                log.info(multiStreamReq.toString());

                MessageResponse build = MessageResponse.newBuilder().setId(multiStreamReq.getId())
                        .setTime(System.nanoTime()).build();

                resp.onNext(build);
//                resp.onCompleted();
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
    }
}
