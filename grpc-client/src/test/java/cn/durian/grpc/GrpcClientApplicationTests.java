package cn.durian.grpc;

import cn.durian.grpc.service.GrpcMultiStreamClientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class GrpcClientApplicationTests {

    @Autowired
    private GrpcMultiStreamClientService service;

    @Test
    void contextLoads() throws Exception {
        service.queryByStream();
    }

}
