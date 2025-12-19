package be.vdab.movies2.movies;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void increaseRankingVerhoogtRankingMet1(){
        Movie movie = new Movie("test1",(short)2000, BigDecimal.ONE,new Distributor("test1"));
        movie.increaseRankingWithOne();
        assertThat(movie.getRanking()).isEqualTo(BigDecimal.valueOf(2));
    }
      @Test
    void getCommentsGeeftDeNodigeCommentaren(){
          Movie movie = new Movie("test1",(short)2000, BigDecimal.ONE,new Distributor("test1"));
          var commentaar1 = new Comment("test1@test.info","test1","eerst");
          var commentaar2 = new Comment("test2@test.info","test2","tweede");
          Set<Comment> commentaren= new LinkedHashSet<>();
          commentaren.add(commentaar1);
          commentaren.add(commentaar2);
          movie.setComments(commentaren);
          assertThat(movie.getComments()).isEqualTo(commentaren);
      }
}