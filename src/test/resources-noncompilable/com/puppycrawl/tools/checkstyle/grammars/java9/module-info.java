/** header */
module jdk.naming.rmi {
    requires java.naming;
    requires java.rmi;
    provides javax.naming.spi.InitialContextFactory
        with com.sun.jndi.rmi.registry.RegistryContextFactory;

    // temporary export until NamingManager.getURLContext uses services
    exports com.sun.jndi.url.rmi to java.naming;
    exports com.sun.jndi.rmi.registry to java.rmi;
}