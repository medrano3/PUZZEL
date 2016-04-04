package owenf10.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture num[];
    Texture reset, winner;
    int puzzle[][];
    Sound sound;
    boolean win=false;

    @Override
    public void create() {

        batch = new SpriteBatch();
        num = new Texture[9];
        reset = new Texture("reset.png");
        winner = new Texture("win.png");
        puzzle = new int[3][3];
        sound = Gdx.audio.newSound(Gdx.files.internal("click1.wav"));

        for (int i = 0; i < 9; i++) {
            num[i] = new Texture("num" + i + ".png");

        }
        reset();
}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        for(int x=0; x<3;x++){
            for(int y=0;y<3;y++){
                batch.draw(num[puzzle[y][x]], 150*x, 150*y);
            }
        }

        batch.draw(reset, 480, 250);
        if(win == true) {
            batch.draw(winner, 0, 0);
        }

		batch.end();
        if(Gdx.input.justTouched()){
            int click_x = Gdx.input.getX();
            int click_y = Gdx.graphics.getHeight()-Gdx.input.getY();

            if(click_x>480 && click_x<580 && click_y>250 && click_y<350) {
                reset();
            }
            for(int x=0; x<3; x++){
                for(int y=0;y<3;y++){
                    if(click_x>x*150 && click_x<x*150+150 && click_y>y*150 && click_y<y*150+150) {
                        System.out.println("Touch " + y + "," + x);

                        if(y+1<3 && puzzle[y+1][x]==0){//0 a la derecha
                            puzzle[y+1][x]=puzzle[y][x];
                            puzzle[y][x]=0;
                            sound.play();
                        }
                        if(y-1>=0 && puzzle[y-1][x]==0){//0 a la izquierda
                            puzzle[y-1][x]=puzzle[y][x];
                            puzzle[y][x]=0;
                            sound.play();
                        }
                        if(x+1<3 && puzzle[y][x+1]==0){//0 a arriba
                            puzzle[y][x+1]=puzzle[y][x];
                            puzzle[y][x]=0;
                            sound.play();
                        }
                        if(x-1>=0 && puzzle[y][x-1]==0){// 0 a abajo
                            puzzle[y][x-1]=puzzle[y][x];
                            puzzle[y][x]=0;
                            sound.play();
                        }


                  }
                }
            }
            if(puzzle[0][0]==7 &&  puzzle[0][1]==8 && puzzle[0][2]==0 && puzzle[1][0]==4 && puzzle[1][1]==5 && puzzle[1][2]==6 && puzzle[2][0]==1 && puzzle[2][1]==2 && puzzle[2][2]==3){
                System.out.println("Ha ganado");
                win = true;

            }


        }

	}

    public void reset() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                puzzle[i][j] = -1;


        for (int i = 0; i < 9; i++) {
            int x_rand = (int) (Math.random() * 10 % 3);
            int y_rand = (int) (Math.random() * 10 % 3);
            while (puzzle[y_rand][x_rand] != -1) {
                x_rand = (int) (Math.random() * 10 % 3);
                y_rand = (int) (Math.random() * 10 % 3);
            }
            puzzle[y_rand][x_rand] = i;
        }
    }
}
