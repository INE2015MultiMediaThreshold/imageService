package business;

import java.awt.image.BufferedImage;
import java.util.Random;  
  
public class NoiseAdditionFilter extends AbstractBufferedImageOp {  
    public final static double MEAN_FACTOR = 2.0;  
    public final static int POISSON_NOISE_TYPE = 2;  
    public final static int GAUSSION_NOISE_TYPE = 1;  
    private double _mNoiseFactor = 10;  
    private int _mNoiseType = GAUSSION_NOISE_TYPE;  
      
    public NoiseAdditionFilter() {  
        System.out.println("Adding Poisson/Gaussion Noise");  
    }  
      
    public void setNoise(double power) {  
        this._mNoiseFactor = power;  
    }  
      
    public void setNoiseType(int type) {  
        this._mNoiseType = type;  
    }  
      
    @Override  
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {  
        int width = src.getWidth();  
        int height = src.getHeight();  
        Random random = new Random();  
        if ( dest == null )  
            dest = createCompatibleDestImage( src, null );  
  
        int[] inPixels = new int[width*height];  
        int[] outPixels = new int[width*height];  
        getRGB( src, 0, 0, width, height, inPixels );  
        int index = 0;  
        for(int row=0; row<height; row++) {  
            int ta = 0, tr = 0, tg = 0, tb = 0;  
            for(int col=0; col<width; col++) {  
                index = row * width + col;  
                ta = (inPixels[index] >> 24) & 0xff;  
                tr = (inPixels[index] >> 16) & 0xff;  
                tg = (inPixels[index] >> 8) & 0xff;  
                tb = inPixels[index] & 0xff;  
                if(_mNoiseType == POISSON_NOISE_TYPE) {  
                    tr = clamp(addPNoise(tr, random));  
                    tg = clamp(addPNoise(tg, random));  
                    tb = clamp(addPNoise(tb, random));  
                } else if(_mNoiseType == GAUSSION_NOISE_TYPE) {  
                    tr = clamp(addGNoise(tr, random));  
                    tg = clamp(addGNoise(tg, random));  
                    tb = clamp(addGNoise(tb, random));  
                }  
                outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;  
            }  
        }  
  
        setRGB( dest, 0, 0, width, height, outPixels );  
        return dest;  
    }  
      
    private int addGNoise(int tr, Random random) {  
        int v, ran;  
        boolean inRange = false;  
        do {  
            ran = (int)Math.round(random.nextGaussian()*_mNoiseFactor);  
            v = tr + ran;  
            // check whether it is valid single channel value  
            inRange = (v>=0 && v<=255);   
            if (inRange) tr = v;  
        } while (!inRange);  
        return tr;   
    }  
  
    public static int clamp(int p) {  
        return p > 255 ? 255 : (p < 0 ? 0 : p);  
    }  
      
    private int addPNoise(int pixel, Random random) {  
        // init:  
        double L = Math.exp(-_mNoiseFactor * MEAN_FACTOR);  
        int k = 0;  
        double p = 1;  
        do {  
            k++;  
            // Generate uniform random number u in [0,1] and let p ¡û p ¡Á u.  
            p *= random.nextDouble();  
        } while (p >= L);  
        double retValue = Math.max((pixel + (k - 1) / MEAN_FACTOR - _mNoiseFactor), 0);  
        return (int)retValue;  
    }  
  
}  