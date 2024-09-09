package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.Asset;
import org.springboot.tradenow.Entity.Coin;
import org.springboot.tradenow.Entity.User;

import java.util.List;

public interface AssetImpl {

    Asset createAsset(User user, Coin coin, double quantity);
    Asset getAssetById(Long assetId) throws Exception;
    Asset getAssetByUserIdAndId(Long userId, Long id);
    List<Asset> getUserAssets(Long userId);
    Asset updateAsset(Long assetId, double quantity) throws Exception;
    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);
    void deleteAsset(Long assetId);

}
