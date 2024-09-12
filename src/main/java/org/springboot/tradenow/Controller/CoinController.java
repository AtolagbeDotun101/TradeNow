package org.springboot.tradenow.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springboot.tradenow.Entity.Coin;
import org.springboot.tradenow.Service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("coins")
public class CoinController {
    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Coin>> getCoins(@RequestParam int page) throws Exception {
        List<Coin> coins =  coinService.getCoins(page);
        return  ResponseEntity.status(HttpStatus.OK).body(coins);
    }

    @GetMapping("/{coinId}/chart")
    public ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId, @RequestParam("days")int days) throws Exception {
        String marketChart = coinService.getMarketChart(coinId,days);
        JsonNode res = objectMapper.readTree(marketChart);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{coinId}/search")
    public ResponseEntity<JsonNode> searchCoin(@RequestParam("query")String keyword) throws Exception {
        String searchCoin = coinService.searchCoin(keyword);
        JsonNode res = objectMapper.readTree(searchCoin);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/top50")
    public ResponseEntity<JsonNode> getTop50Coins() throws Exception {
        String top50CoinByMarketChart = coinService.getTop50CoinByMarketChart();
        JsonNode res = objectMapper.readTree(top50CoinByMarketChart);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/trending")
        public ResponseEntity<JsonNode> getTrendingCoins() throws Exception {
        String trendingCoins = coinService.getTrendingCoins();
        JsonNode res = objectMapper.readTree(trendingCoins);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/details/{coinId}")
    public ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception {
        String coinDetails = coinService.getCoinDetails(coinId);
        JsonNode res = objectMapper.readTree(coinDetails);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


}
