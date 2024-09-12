package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.Coin;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Watchlist;

public interface WatchlistImpl {
    Watchlist findUserWatchlist(Long userId) throws Exception;
    Watchlist createUserWatchlist(User user);
    Watchlist findById(Long id) throws Exception;
    Coin addCoinToWatchlist(Coin coin, User user) throws Exception;
}
