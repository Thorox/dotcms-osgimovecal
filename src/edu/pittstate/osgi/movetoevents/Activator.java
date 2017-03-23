package edu.pittstate.osgi.movetoevents;

import com.dotmarketing.osgi.GenericBundleActivator;
import org.osgi.framework.BundleContext;

public class Activator extends GenericBundleActivator {

    @Override
    public void start ( BundleContext bundleContext ) throws Exception {

        //Initializing services...
        initializeServices( bundleContext );

        //Registering the test Actionlet
        registerActionlet( bundleContext, new MyActionlet() );
    }

    @Override
    public void stop ( BundleContext bundleContext ) throws Exception {
        unregisterActionlets();
    }

}
