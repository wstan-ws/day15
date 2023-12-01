package vttp.ssf.day15.repo;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.day15.model.Item;

@Repository
public class CartRepository {

    @Autowired @Qualifier("myredis")
	private RedisTemplate<String, String> template;

    public boolean hasCart(String name) {
        return template.hasKey(name);
    }

    public void deleteCart(String name) {

        template.delete(name);
    }
    
    public void addCart(String name, List<Item> cart) {

        ListOperations<String, String> list = template.opsForList();

        cart.stream()
            .forEach(item -> {list.leftPush(name, "%s, %d".formatted(item.getName(), item.getQuantity()));
            });
    }

    public List<Item> getCart(String name) {
        
        ListOperations<String, String> list = template.opsForList();

        Long size = list.size(name);

        List<Item> cart = new LinkedList<>();

        for (String i : list.range(name, 0, size)) {
            String[] terms = i.split(",");
            Item item = new Item();
            item.setName(terms[0].trim());
            item.setQuantity(Integer.parseInt(terms[1].trim()));
            cart.add(item);
        }

        return cart;
    }
    
    
}
