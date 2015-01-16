dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            username = "root"
            password = "password"
            dbCreate = "update" 			// one of 'create', 'create-drop', 'update', 'validate', ''
            //loggingSql  = true
            url = "jdbc:mysql://localhost:3306/forum"
        }
    }
    test {
        dataSource {
            username = "root"
            password = "password"
            dbCreate = "update" 			// one of 'create', 'create-drop', 'update', 'validate', ''
            //loggingSql  = true
            url = "jdbc:mysql://localhost:3306/forum"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            jndiName = "java:comp/env/forum"
        }
    }
}
