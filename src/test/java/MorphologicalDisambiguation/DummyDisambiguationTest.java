package MorphologicalDisambiguation;

import MorphologicalAnalysis.FsmMorphologicalAnalyzer;
import MorphologicalAnalysis.FsmParse;
import MorphologicalAnalysis.FsmParseList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.*;

public class DummyDisambiguationTest {

    @Test
    public void testDisambiguation() {
        FsmMorphologicalAnalyzer fsm = new FsmMorphologicalAnalyzer();
        DisambiguationCorpus corpus = new DisambiguationCorpus("penntreebank.txt");
        DummyDisambiguation algorithm = new DummyDisambiguation();
        int correctParse = 0;
        int correctRoot = 0;
        for (int i = 0; i < corpus.sentenceCount(); i++){
            FsmParseList[] sentenceAnalyses = fsm.robustMorphologicalAnalysis(corpus.getSentence(i));
            ArrayList<FsmParse> fsmParses =  algorithm.disambiguate(sentenceAnalyses);
            for (int j = 0; j < corpus.getSentence(i).wordCount(); j++){
                DisambiguatedWord word = (DisambiguatedWord) corpus.getSentence(i).getWord(j);
                if (fsmParses.get(j).transitionList().toLowerCase(new Locale("tr")).equals(word.getParse().toString().toLowerCase(new Locale("tr")))){
                    correctParse++;
                    correctRoot++;
                } else {
                    if (fsmParses.get(j).getWord().equals(word.getParse().getWord())){
                        correctRoot++;
                    }
                }
            }
        }
        assertEquals(0.8618, (correctRoot + 0.0) / corpus.numberOfWords(), 0.002);
        assertEquals(0.7021, (correctParse + 0.0) / corpus.numberOfWords(), 0.002);
    }

}