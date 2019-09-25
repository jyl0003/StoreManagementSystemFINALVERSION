public class test {

        public static void main(String[] args) {
            String hi = encode("Prespecialized");
            System.out.println(hi);
        }
        static String encode(String word){
            word = word.toLowerCase();
            int[] char1 = new int[512];
            for (int i = 0; i < word.length(); i++) {
                char1[word.charAt(i)]++;
                char chs = word.charAt(i);
                int he = (char1[word.charAt(i)]);
                int hq = he;
            }
            for (int j =0; j < word.length(); j++) {
                char erf = word.charAt(j);
                int we = (char1[word.charAt(j)]);
                int tr = we;
                if (char1[word.charAt(j)] > 1) {
                    word = word.replace(word.charAt(j), ')');
                }
                else if (char1[word.charAt(j)] == 1) {
                    word = word.replace(word.charAt(j), '(');
                }
            }
            return word;
        }

    }
