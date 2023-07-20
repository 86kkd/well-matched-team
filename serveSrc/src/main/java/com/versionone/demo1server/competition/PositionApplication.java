package com.versionone.demo1server.competition;

import com.acoinfo.vsoa.Constant;
import com.acoinfo.vsoa.Position;

import java.net.InetSocketAddress;

/**
 * VSOA位置服务类
 */
public class PositionApplication {
    public static Position.PositionItem servers[] = {
            new Position.PositionItem("light_server", Constant.AF_INET, "127.0.0.1", 3001, false),
            new Position.PositionItem("axis_server", Constant.AF_INET, "127.0.0.1", 3002, false)
    };

    static Position position;
    public static void main(String[] args) {
        position = new Position(servers, new InetSocketAddress("127.0.0.1", 3000));

        position.start();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
