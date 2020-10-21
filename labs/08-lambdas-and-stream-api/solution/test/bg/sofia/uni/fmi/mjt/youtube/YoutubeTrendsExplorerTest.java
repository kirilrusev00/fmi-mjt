package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class YoutubeTrendsExplorerTest {
    private static Set<TrendingVideo> trendingVideos;
    private static YoutubeTrendsExplorer explorer;

    private static String[] videos = {
        "video_id\ttrending_date\ttitle\tpublish_time\ttags\tviews\tlikes\tdislikes\t\t\t\t\t\t\t\t\t\n",

        "_6uvQtccJss\t17.01.12\tBLACK FRIDAY HAUL 2017 + TRY ON | Bethany Mota\t2017-11-25T18:55:42.000Z" +

            "\t\"bethany mota|\"\"bethany moto\"\"|\"\"bethany\"\"|\"\"mota\"\"|\"\"macbarbie07\"\"|\"" +

            "\"black friday haul\"\"|\"\"black friday\"\"|\"\"haul\"\"|\"\"clothing\"\"|\"\"forever 21\"\"|\"" +

            "\"victorias secret\"\"|\"\"bath and body works\"\"|\"\"pacsun\"\"|\"\"shopping\"\"|\"\"try on\"" +

            "\"|\"\"trying on\"\"|\"\"winter outfits\"\"|\"\"christmas\"\"|\"\"giveaway\"\"\"\t1281002\t74074" +

            "\t1208\t\t\t\t\t\t\t\t\t\n",

        "WLagbArQsuE\t17.03.12\tImagine Dragons - Thunder (Live On The Ellen DeGeneres Show/2017)\t2017-11-27" +

            "T19:57:14.000Z\tImagine Dragons Thunder Evolve Ellen DeGeneres Ellen show\t657286\t30831\t354" +

            "\t\t\t\t\t\t\t\t\t\n",

        "BLts9UN_K3g\t17.05.12\tPokémon Challenge: Watch GAME FREAK’s Kazumasa Iwao Guess the Pokémon!" +

            "\t2017-11-30T16:11:54.000Z\tMagikarp\t98894\t4472\t85\t\t\t\t\t\t\t\t\t\n",

        "gvZW4Lh-8Mc\t17.06.12\tFirst Time Seeing Daddy Without a Beard!\t2017-11-29T14:58:15.000Z\t[none]" +

            "\t71696\t181\t8\t\t\t\t\t\t\t\t\t\n",

        "WKrnYjpmcpE\t18.15.01\tphilbert's pet\t2018-01-12T21:26:39.000Z\t" +

            "\"Berd|\"\"animation\"\"|\"\"comedy\"\"\"\t183038\t11042\t211\t\t\t\t\t\t\t\t\t\n" };

    @BeforeClass
    public static void beforeClass() throws IOException {
        final int skipTableDescriptionLine = 1;
        InputStream stream = new ByteArrayInputStream(
                String.join("", videos)
                        .getBytes());

        try (InputStreamReader isr = new InputStreamReader(stream);
             BufferedReader reader = new BufferedReader(isr)) {

            trendingVideos = reader.lines()
                    .skip(skipTableDescriptionLine)
                    .map(TrendingVideo::createTrendingVideo)
                    .collect(Collectors.toSet());
        }

        InputStream explorerStream = new ByteArrayInputStream(
                Arrays.stream(videos)
                        .collect(Collectors.joining())
                        .getBytes());
        explorer = new YoutubeTrendsExplorer(explorerStream);
    }

    @Test
    public void testEqualsTrendingVideos() {
        TrendingVideo tv1 = TrendingVideo.createTrendingVideo(videos[1]);
        TrendingVideo tv2 = TrendingVideo.createTrendingVideo(videos[2]);
        String assertMessage = "Videos should not be equal.";
        assertFalse(assertMessage, tv1.equals(tv2));
    }

    @Test
    public void testIfExistingDatasetIsLoadedCorrectly() {
        String assertMessage = "Number of movies in the MoviesExplorer does not match the movies in the dataset.";
        final int expected = trendingVideos.size();
        final int actual = explorer.getTrendingVideos().size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindIdOfLeastLikedVideo() {
        String assertMessage = "ID of least liked video is not correct.";
        final String expected = "gvZW4Lh-8Mc";
        String actual = explorer.findIdOfLeastLikedVideo();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindIdOfMostLikedLeastDislikedVideo() {
        String assertMessage = "ID of most liked and least disliked video is not correct.";
        final String expected = "_6uvQtccJss";
        String actual = explorer.findIdOfMostLikedLeastDislikedVideo();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindDistinctTitlesOfTop3VideosByViews() {
        String assertMessage = "The top 3 videos by views are not the same.";
        final int topVideos = 3;
        Collection<String> sortedVideos = trendingVideos.stream()
                .sorted(Comparator.comparing(TrendingVideo::getViews).reversed())
                .limit(topVideos)
                .map(TrendingVideo::getTitle)
                .collect(Collectors.toList());

        List<String> expected = new ArrayList<String>(sortedVideos);
        List<String> actual = new ArrayList<String>(explorer.findDistinctTitlesOfTop3VideosByViews());

        assertTrue(assertMessage, expected.equals(actual));
    }

    @Test
    public void testFindIdOfMostTaggedVideo() {
        String assertMessage = "ID of most tagged video is not correct.";
        final String expected = "_6uvQtccJss";
        String actual = explorer.findIdOfMostTaggedVideo();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindTitleOfFirstVideoTrendingBefore100KViews() {
        String assertMessage = "Title of first video trending before 100K views is not correct.";
        final String expected = "First Time Seeing Daddy Without a Beard!";
        String actual = explorer.findTitleOfFirstVideoTrendingBefore100KViews();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindIdOfMostTrendingVideo() {
        String assertMessage = "ID of most trending video is not correct.";
        final String expected = "WLagbArQsuE";
        String actual = explorer.findIdOfMostTrendingVideo();

        assertEquals(assertMessage, expected, actual);
    }
}
