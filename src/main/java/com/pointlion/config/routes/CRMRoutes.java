package com.pointlion.config.routes;

import com.jfinal.config.Routes;

public class CRMRoutes extends Routes {

    @Override
    public void config() {
        setBaseViewPath("/WEB-INF/admin/crm");
    }
}
