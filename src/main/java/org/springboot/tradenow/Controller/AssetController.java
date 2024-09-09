package org.springboot.tradenow.Controller;

import org.springboot.tradenow.Entity.Asset;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Helper.UserServiceImpl;
import org.springboot.tradenow.Service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(@RequestHeader("Authorization")String jwt, @PathVariable String coinId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Asset>> getUserAsset(@RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Asset> asset = assetService.getUserAssets(user.getId());
        return ResponseEntity.ok(asset);
    }
}
