public class Trailers extends Room {
   // Functional cohesion

   public Trailers(String name, int x, int y, int h, int w, String[] neighbors) {
      this.setName(name);
      this.setLocationX(x);
      this.setLocationY(y);
      this.setWidth(w);
      this.setHeight(h);
      this.setNeighbors(neighbors);
   }

   public boolean isSet() {
      return false;
   }

   public boolean hasSceneCard() {
      return false;
   }

}