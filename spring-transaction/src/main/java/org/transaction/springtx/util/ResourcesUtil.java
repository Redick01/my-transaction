package org.transaction.springtx.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liupenghui
 * @date 2021/12/1 4:51 下午
 */
public class ResourcesUtil {

    public static Resource[] resolveMapperLocations(String path) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>();
        mapperLocations.add(path);
        List<Resource> resources = new ArrayList<Resource>();
        for (String mapperLocation : mapperLocations) {
            try {
                Resource[] mappers = resourceResolver.getResources(mapperLocation);
                resources.addAll(Arrays.asList(mappers));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resources.toArray(new Resource[0]);
    }
}
