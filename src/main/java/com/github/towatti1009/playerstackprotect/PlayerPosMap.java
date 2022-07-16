package com.github.towatti1009.playerstackprotect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerPosMap {	
	private static Map<String,List<Location>> posRecord = new HashMap<>();
	
	//任意のエントリを取得（つかってない）
	/*public static Location getEntries(String pl,int pos) {
		return posRecord.get(pl).get(pos);
	}*/
	
	//ホントはオーバーロードするべきなんだけどなんとなく別メソッド
	//最新のエントリを取得
	public static Location getLatestEntries(String pl) {
		List<Location> sel = posRecord.get(pl);
		return sel.get(sel.size() - 1);
	}
	
	//最新のエントリを削除
	public static void removeLatestEntries(String pl) {
		int size = posRecord.get(pl).size();
		if(size > 1)
			posRecord.get(pl).remove(size - 1);
	}
	
	//指定キー（プレイヤーデータ）を削除
	public static void deleteKeys(String pl) {
		if(posRecord.containsKey(pl))
			posRecord.remove(pl);
	}
	
	//全データ削除
	public static void deleteKeys(boolean confirm) {
		if(confirm)posRecord.clear();
	}
	
	//エントリの追加を試行する
	public static boolean tryAddEntries(String pl, Location lc, Player pld) {
		boolean debug = Config.getBooleanConfig("debug");
		//指定されたキーが存在しなかった場合の初期化処理分岐
		if(!posRecord.containsKey(pl)) {
			if(debug)pld.sendMessage("DEBUG:エントリが存在しなかったため新規作成します");
			initEntries(pl,lc);
			return false;
		}
		
		//このlenは比較/値取得用,書き込みには使用しないこと
		List<Location> sel = posRecord.get(pl);
		int len = sel.size();
		
		//記録するワールドを制限する処理をここに入れる
		//一定距離以上離れている場合のみエントリ追加
		if(distanceCalc3dSpace(sel.get(len-1), lc, Config.getIntegerConfig("DistanceThreshold"), pld)) {
			if(debug)pld.sendMessage("DEBUG:一定距離以上離れています");
			//記録する座標の数をここで指定する
			if(len >= Config.getIntegerConfig("LocationSaveCount")) {
				if(debug)pld.sendMessage("DEBUG:要素数上限です");
				posRecord.get(pl).remove(0);
			}
			if(debug)pld.sendMessage("DEBUG:エントリを追加します");
			posRecord.get(pl).add(lc);
		}
		//pld.sendMessage(Config.getVAR(lc));
		return true;
	}
	//ポインタが欲しい人生だった
	//posRecord.~~の記述減らしたい
	
	//初期化処理
	public static void initEntries(String pl, Location lc) {
		posRecord.put(pl, new ArrayList<Location>());
		posRecord.get(pl).add(lc);
	}
	
	//3次元距離測定
	private static boolean distanceCalc3dSpace(Location from, Location to, int threshold, Player pl) {
		double[] posF = {from.getX(),from.getY(),from.getZ()};
		double[] posT = {to.getX(),to.getY(),to.getZ()};
		double distance = Math.sqrt(Math.pow((posF[0]-posT[0]),2) + Math.pow((posF[1]-posT[1]),2) + Math.pow((posF[2]-posT[2]),2));
		if(Config.getBooleanConfig("debug"))pl.sendMessage("DEBUG:距離="+distance+",しきい値="+threshold);
		
		if(distance >= threshold)
			return true;
		else
			return false;
	}
}
