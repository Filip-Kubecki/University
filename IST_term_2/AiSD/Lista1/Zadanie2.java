package Lista1;
//Możliwość zastosowania wzorca projektowego Strategia dobierającego algorytm
public class Zadanie2 {
    public final static int d = 256;
    public static void main(String[] args) {
        String sentence = "Litw0=0jczdr0yzno moja, Ty jestes jak zdr0w13, ile C13=c3ndr01c, t3n ty1k0 si3 d0wie=_kt0 C13 stracil.";
        String pattern = "dr0";
//        System.out.println(sentence);
//        pairSwap(sentence);

        int prevIndex = 0;
        while (ShiftOr(sentence.substring(prevIndex),pattern) != -1){
            System.out.println(ShiftOr(sentence.substring(prevIndex),pattern)+prevIndex);
            prevIndex += ShiftOr(sentence.substring(prevIndex),pattern)+1;
        }
    }

    public static void pairSwap(String text){
        String[] textArray = text.split(" ");
        for (int i = 0; i < textArray.length; i++) {
            String word = textArray[i];
            if (checkWord(word)) textArray[i] = turn(word, ShiftOr(word,"="));
        }
        for (String s : textArray) {
            System.out.print(s+" ");
        }
    }

    public static Boolean checkWord(String word){
        if (!word.contains("=")){
            return false;
        }
        String[] wordAr = word.split("=");
        for (String s : wordAr) {
            if (s.charAt(0) != '_' && !Character.isLetter(s.charAt(0))) {
                return false;
            }
        }
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != '=' && word.charAt(i) != ',' && word.charAt(i) != '.' && word.charAt(i) != '_' && !Character.isDigit(word.charAt(i)) && !Character.isAlphabetic(word.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public static String turn(String word,int index){
        if (word.charAt(word.length()-1) == ',' || word.charAt(word.length()-1) == '.'){
            return word.substring(index+1,word.length()-1)+"="+word.substring(0,index)+word.charAt(word.length()-1);
        }
        return word.substring(index+1)+"="+word.substring(0,index);
    }

    public static int ShiftOr(String string, String pattern){
        int patternMaskSize = 256;
        int patternMaxLen = 63;
        char[] patternArray = pattern.toCharArray();
        char[] stringArray = string.toCharArray();
        int patternLen = pattern.length();
        int[] patternMask = new int[patternMaskSize];
        int R = ~1;
//        Check if pattern exists
        if (patternLen == 0){
            return -1;
        }
//        Check if pattern isn't too long
        if (patternLen > patternMaxLen){
            System.out.println("Too long pattern");
            return -1;
        }
//        Prepare patternMask array with negated 0 values
        for (int i = 0; i <= patternMaskSize-1; i++) {
            patternMask[i] = ~0;
        }
        for (int i = 0; i < patternLen; i++) {
            patternMask[patternArray[i]] &= ~(1L << i);
        }
        for (int i = 0; i < string.length(); ++i) {
            R |= patternMask[stringArray[i]];
            R <<= 1;
            if ((R & (1L << patternLen)) == 0)
                return i - patternLen + 1;
        }
        return -1;
    }
    public static void RabinKarp(String pat, String txt, int q) {
        int M = pat.length();
        int N = txt.length();
        int i, j;
        int p = 0; // hash value for pattern
        int t = 0; // hash value for txt
        int h = 1;

        // The value of h would be "pow(d, M-1)%q"
        for (i = 0; i < M - 1; i++)
            h = (h * d) % q;

        // Calculate the hash value of pattern and first
        // window of text
        for (i = 0; i < M; i++) {
            p = (d * p + pat.charAt(i)) % q;
            t = (d * t + txt.charAt(i)) % q;
        }

        // Slide the pattern over text one by one
        for (i = 0; i <= N - M; i++) {

            // Check the hash values of current window of
            // text and pattern. If the hash values match
            // then only check for characters one by one
            if (p == t) {
                /* Check for characters one by one */
                for (j = 0; j < M; j++) {
                    if (txt.charAt(i + j) != pat.charAt(j))
                        break;
                }

                // if p == t and pat[0...M-1] = txt[i, i+1,
                // ...i+M-1]
                if (j == M)
                    System.out.println(
                            "Pattern found at index " + i);
            }

            // Calculate hash value for next window of text:
            // Remove leading digit, add trailing digit
            if (i < N - M) {
                t = (d * (t - txt.charAt(i) * h)
                        + txt.charAt(i + M))
                        % q;

                // We might get negative value of t,
                // converting it to positive
                if (t < 0)
                    t = (t + q);
            }
        }
    }


}
