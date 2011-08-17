package org.mule;

import java.net.URL;
import junit.framework.TestCase;

public class MuleTestCase extends TestCase
{
    public void testMuleConfigIsAttached()
    {
        URL muleConfigUrl = getClass().getClassLoader().getSystemResource("mule-config.xml");
        assertNotNull(muleConfigUrl);
    }
}
