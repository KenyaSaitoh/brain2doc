package pro.kensait.brain2doc.openai;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;

public class StaticProxySelector extends ProxySelector {
    private final List<Proxy> proxyList;

    public StaticProxySelector(Proxy.Type proxyType, InetSocketAddress proxyAddress) {
        proxyList = List.of(new Proxy(proxyType, proxyAddress));
    }

    @Override
    public List<Proxy> select(URI uri) {
        return proxyList;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        throw new RuntimeException("プロキシへの接続エラー");
    }
}