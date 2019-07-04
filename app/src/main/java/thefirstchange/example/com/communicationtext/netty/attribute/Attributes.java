package thefirstchange.example.com.communicationtext.netty.attribute;

import io.netty.util.AttributeKey;
import thefirstchange.example.com.communicationtext.netty.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
