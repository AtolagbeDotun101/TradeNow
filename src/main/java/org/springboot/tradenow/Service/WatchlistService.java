package org.springboot.tradenow.Service;

import org.springboot.tradenow.Entity.Coin;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Watchlist;
import org.springboot.tradenow.Repository.Implementation.WatchlistImpl;
import org.springboot.tradenow.Repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchlistService implements WatchlistImpl {
    @Autowired
    private WatchlistRepository watchlistRepository;

    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepository.findByUserId(userId);
        if (watchlist == null) {
            throw new Exception("Watchlist not found!");
        }
        return watchlist;
    }

    @Override
    public Watchlist createUserWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlist = watchlistRepository.findById(id);
        if (watchlist.isEmpty()) {
            throw new Exception("Watchlist not found");
        }
        return watchlist.get();
    }

    @Override
    public Coin addCoinToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist = findUserWatchlist(user.getId());
        if(watchlist.getCoins().contains(coin)){
            watchlist.getCoins().remove(coin);
        }else watchlist.getCoins().add(coin);
        watchlistRepository.save(watchlist);
        return coin;
    }
}
