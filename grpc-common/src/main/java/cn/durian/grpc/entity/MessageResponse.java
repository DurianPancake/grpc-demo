package cn.durian.grpc.entity;

import lombok.Data;

/**
 * @author Liuluhao
 * @Date 2020/8/27
 */
@Data
public class MessageResponse {

    private String id;

    private Long time;

    private byte[] data;
}
