package gui.managers;

import java.util.HashMap;

public class ScoreManager {
    private static ScoreManager scoreManagerInstance = null;
    private static boolean firstRound = true;
    private HashMap<String, Long> scores;

    private ScoreManager(){
    }

    public static ScoreManager getInstance(){
        if(scoreManagerInstance == null)
            scoreManagerInstance = new ScoreManager();
        return scoreManagerInstance;
    }

    public HashMap<String, Long> getScores(){
        return scores;
    }

    public String getValueFromKey(String key){
        return String.valueOf(scores.get(key));
    }

    public void updateScores(HashMap<String, Long> map){
        if(firstRound == true){
            scores = new HashMap<>(map);
            firstRound = false;
        }
        else{
            for(String key : map.keySet()){
                if(scores.containsKey(key)){
                  scores.replace(key, scores.get(key) + map.get(key)) ;
                }
            }
        }
    }
}
