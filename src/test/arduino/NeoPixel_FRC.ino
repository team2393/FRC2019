/** Adafruit 'NeoPixel' RGB Demo for robotics
 *  
 *  To use, copy the Adafruit NeoPixel library
 *  in the Arduino 'library' folder.
 */

// Length of the NeoPixel strand (30 LEDs per meter)
#define NUM_PIXELS 30

// Arduino pin where strand is connected
// (Black to ground, white to this pin)
#define PIN 6


#include <Adafruit_NeoPixel.h>
typedef uint32_t Color;
#ifdef __AVR__
  #include <avr/power.h>
#endif

Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_PIXELS, PIN, NEO_GRB + NEO_KHZ800);

void setup()
{
  Serial.begin(9600);
  Serial.println("* Neopixel FRC *");
  Serial.print("NEO : ");
  Serial.println(PIN); 

  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
}

void loop()
{
  demo();
//   flag();
//   one_at_a_time(strip.Color(0, 255, 0));
//   one_at_a_time(strip.Color(255, 0, 0));
//   one_at_a_time(strip.Color(0, 0, 255));
//   one_at_a_time(strip.Color(255, 0, 255));
//   off();
//   delay(500);
//   red();
//   delay(500);
}

void off()
{
  Color c = strip.Color(0, 0, 0);
  for(int i=0; i<strip.numPixels(); ++i)
    strip.setPixelColor(i, c);
  strip.show();
}

void red()
{
  Color c = strip.Color(255, 0, 0);
  for(int i=0; i<strip.numPixels(); ++i)
    strip.setPixelColor(i, c);  
  strip.show();
}

void one_at_a_time(Color color)
{
  Color black = strip.Color(0, 0, 0);
  int i=0;
  while (i<strip.numPixels())
  {
    strip.setPixelColor(i, color);
    strip.show();
    delay(10);
    strip.setPixelColor(i, black);
    strip.show();

    ++i;
  }
}

void flag()
{
  Color red = strip.Color(255, 0, 0);
  Color white = strip.Color(255, 255, 255);
  Color blue = strip.Color(0, 0, 255);

  for (int shift=0; shift<30; ++shift)
  {
    for (int i=0; i<10; ++i)
        strip.setPixelColor( (shift+i) % 30, red);  
    for (int i=10; i<20; ++i)
        strip.setPixelColor( (shift+i) % 30, white);  
    for (int i=20; i<30; ++i)
        strip.setPixelColor( (shift+i) % 30, blue);  
    strip.show();
    delay(50);
  }
  for (int shift=30; shift>0; --shift)
  {
    for (int i=0; i<10; ++i)
        strip.setPixelColor( (shift+i) % 30, red);  
    for (int i=10; i<20; ++i)
        strip.setPixelColor( (shift+i) % 30, white);  
    for (int i=20; i<30; ++i)
        strip.setPixelColor( (shift+i) % 30, blue);  
    strip.show();
    delay(50);
  }
}



void demo()
{
  // Red pixel
  rider(strip.Color(0, 0, 0), strip.Color(255, 0, 0), 25);
  // Fill red
  colorWipe(strip.Color(255, 0, 0), 25);
  // Green pixel
  rider(strip.Color(255, 0, 0), strip.Color(0, 255, 0), 25);
  // Fill green
  colorWipe(strip.Color(0, 255, 0), 25);
  // Blue pixel
  rider(strip.Color(0, 255, 0), strip.Color(0, 0, 255), 25);
  // Fill blue
  colorWipe(strip.Color(0, 0, 255), 25); // Blue
  // White pixel
  rider(strip.Color(0, 0, 255), strip.Color(127, 127, 127), 25);

  rainbow(15);
  
  // Theater pixel chase in...
  theaterChase(strip.Color(127, 127, 127), 50); // White
  theaterChase(strip.Color(127, 0, 0), 50); // Red
  theaterChase(strip.Color(0, 127, 0), 50); // Green
  theaterChase(strip.Color(0, 0, 127), 50); // Blue

  rainbowCycle(15);
  // theaterChaseRainbow(25);
}


// Rider-type pixel over base
void rider(Color base, Color pixel, int wait)
{
  for(int i=0; i<strip.numPixels(); i++)
    strip.setPixelColor(i, base);
  for(int i=0; i<strip.numPixels(); i++)
  {
    strip.setPixelColor(i, pixel);
    strip.show();
    delay(wait);
    strip.setPixelColor(i, base);
  }
  for(int i=strip.numPixels()-1; i>=0; i--)
  {
    strip.setPixelColor(i, pixel);
    strip.show();
    delay(wait);
    strip.setPixelColor(i, base);
  }
}

// Fill the dots one after the other with a color
void colorWipe(Color c, int wait)
{
  for(int i=0; i<strip.numPixels(); i++)
  {
    strip.setPixelColor(i, c);
    strip.show();
    delay(wait);
  }
}

void rainbow(int wait)
{
  int i, j;

  for(j=0; j<256; j++)
  {
    for(i=0; i<strip.numPixels(); i++)
      strip.setPixelColor(i, Wheel((i+j) & 255));
    strip.show();
    delay(wait);
  }
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(int wait)
{
  int i, j;

  for(j=0; j<256*5; j++)
  { // 5 cycles of all colors on wheel
    for(i=0; i< strip.numPixels(); i++)
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    strip.show();
    delay(wait);
  }
}

// Theatre-style crawling lights.
void theaterChase(Color c, int wait)
{
  for (int j=0; j<10; j++)
  {  //do 10 cycles of chasing
    for (int q=0; q < 3; q++)
    {
      for (int i=0; i < strip.numPixels(); i=i+3)
        strip.setPixelColor(i+q, c);    //turn every third pixel on
      strip.show();

      delay(wait);

      for (int i=0; i < strip.numPixels(); i=i+3)
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
    }
  }
}

// Theatre-style crawling lights with rainbow effect
void theaterChaseRainbow(int wait)
{
  for (int j=0; j < 256; j++)
  {     // cycle all 256 colors in the wheel
    for (int q=0; q < 3; q++)
    {
      for (int i=0; i < strip.numPixels(); i=i+3)
        strip.setPixelColor(i+q, Wheel( (i+j) % 255));    //turn every third pixel on
      strip.show();

      delay(wait);

      for (int i=0; i < strip.numPixels(); i=i+3)
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
    }
  }
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
Color Wheel(byte WheelPos)
{
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85)
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  if(WheelPos < 170)
  {
    WheelPos -= 85;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}
