package org.springboot.tradenow.Controller;

import org.springboot.tradenow.Entity.Coin;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Entity.Watchlist;
import org.springboot.tradenow.Helper.UserServiceImpl;
import org.springboot.tradenow.Service.CoinService;
import org.springboot.tradenow.Service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<?> getUserWatchlist(@RequestHeader("Authorization")String jwt)throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());
        return ResponseEntity.ok(watchlist);
    }

//    @PostMapping("/create")
//    public ResponseEntity<?> createUserWatchlist(@RequestHeader("Authorization")String jwt) throws Exception {
//        User user = userService.findUserProfileByJwt(jwt);
//        Watchlist watchlist = watchlistService.createUserWatchlist(user);
//        return ResponseEntity.ok(watchlist);
//    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<?> getUserWatchlistById(@PathVariable("watchlistId") Long watchlistId) throws Exception {
     Watchlist watchlist = watchlistService.findById(watchlistId);
     return ResponseEntity.ok(watchlist);
    }

    @PatchMapping("/add/{coin}")
    public ResponseEntity<?> addToWatchlist(@RequestHeader("Authorization")String jwt,@PathVariable String coin) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coinToAdd = coinService.findCoinById(coin);
        Coin addedCoin = watchlistService.addCoinToWatchlist(coinToAdd, user);
        return ResponseEntity.ok(addedCoin);

    }
}
