import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * Project name(项目名称)：zookeeper_curator创建节点
 * Package(包名): PACKAGE_NAME
 * Class(类名): ZooKeeper
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/20
 * Time(创建时间)： 20:59
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class ZooKeeper
{
    private CuratorFramework client;

    @BeforeEach
    void setUp()
    {
        //重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
        //zookeeper创建链接，第一种
                        /*
                        CuratorFramework client =
                                CuratorFrameworkFactory.newClient("127.0.0.1:2181",
                                        60 * 1000,
                                        15 * 1000,
                                        retryPolicy);
                        client.start();
                        */

        //zookeeper创建链接，第二种
        client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .namespace("test")
                .build();
        client.start();
    }

    @AfterEach
    void tearDown()
    {
        if (client != null)
        {
            client.close();
        }
    }

    @Test
    void test1() throws Exception
    {
        String s = client.create().forPath("/app3");
        System.out.println(s);
    }

    @Test
    void test2() throws Exception
    {
        String s = client.create().forPath("/app4", "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(s);
    }

    @Test
    void test3() throws Exception
    {
        //临时节点
        String s = client.create().withMode(CreateMode.EPHEMERAL).forPath("/app5", "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(s);
    }

    @Test
    void test4() throws Exception
    {
        //临时节点，连续节点
        String s = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/app5", "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(s);
    }

    @Test
    void test5() throws Exception
    {
        String s = client.create().creatingParentsIfNeeded().forPath("/app5/a1", "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(s);
    }


}
