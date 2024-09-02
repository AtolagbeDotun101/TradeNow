package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.Coin;

import java.util.List;

public interface CoinImpl {

    List<Coin> getCoins(int page) throws Exception;

    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findCoinById(String coinId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinByMarketChart() throws Exception;

    String getTrendingCoins() throws Exception;
}
