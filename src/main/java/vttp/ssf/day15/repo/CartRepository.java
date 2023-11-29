package vttp.ssf.day15.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    @Autowired @Qualifier("myredis")
	private RedisTemplate<String, String> template;

    public boolean hasCart(String name) {
        return template.hasKey(name);
    }
    
}
