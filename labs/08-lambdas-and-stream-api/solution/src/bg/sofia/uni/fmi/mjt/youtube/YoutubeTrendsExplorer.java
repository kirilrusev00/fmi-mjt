package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class YoutubeTrendsExplorer {
    private static final int TOP_VIDEOS = 3;
    private static final int DESCRIPTION_OF_TABLE_LINE_NUMBER = 1;
    private static final long TRENDING_VIDEOS_BEFORE = 100_000L;
    private List<TrendingVideo> trendingVideos = null;
    private Map<TrendingVideo, Long> trendingVideosOccurrences = null;

    /**
     * Loads the dataset from the given {@code dataInput} stream.
     */
    public YoutubeTrendsExplorer(InputStream dataInput) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataInput))) {
            trendingVideos = reader.lines()
                    .skip(DESCRIPTION_OF_TABLE_LINE_NUMBER)
                    .map(TrendingVideo::createTrendingVideo)
                    .collect(Collectors.toList());
            trendingVideosOccurrences = trendingVideos.stream()
                    .collect(Collectors.groupingBy(tv -> tv, Collectors.counting()));
        } catch (IOException e) {
            System.out.println("Error while reading from stream.");
        }
    }

    /**
     * Returns all videos loaded from the dataset.
     **/
    public Collection<TrendingVideo> getTrendingVideos() {
        return trendingVideos;
    }

    public String findIdOfLeastLikedVideo() {
        return trendingVideos.stream()
                .min(Comparator.comparing(TrendingVideo::getLikes))
                .get().getId();
    }

    public String findIdOfMostLikedLeastDislikedVideo() {
        return trendingVideos.stream()
                .max(Comparator.comparing((TrendingVideo tv) -> tv.getLikes() - tv.getDislikes()))
                .get().getId();
    }

    public List<String> findDistinctTitlesOfTop3VideosByViews() {
        return trendingVideos.stream()
                .sorted(Comparator.comparing(TrendingVideo::getViews).reversed())
                .distinct()
                .limit(TOP_VIDEOS)
                .map(TrendingVideo::getTitle)
                .collect(Collectors.toList());
    }

    public String findIdOfMostTaggedVideo() {
        return trendingVideos.stream()
                .max(Comparator.comparing((TrendingVideo tv) -> tv.getTags().size()))
                .get().getId();
    }

    public String findTitleOfFirstVideoTrendingBefore100KViews() {
        return trendingVideos.stream()
                .filter((TrendingVideo tv) -> tv.getViews() < TRENDING_VIDEOS_BEFORE)
                .min(Comparator.comparing(TrendingVideo::getPublishDate))
                .get().getTitle();
    }

    public String findIdOfMostTrendingVideo() {
        return trendingVideosOccurrences.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey()
                .getId();
    }

    public static void main(String[] args) {
        Path file = Path.of("USvideos.txt");
        try (InputStream stream = Files.newInputStream(file)) {
            YoutubeTrendsExplorer explorer = new YoutubeTrendsExplorer(stream);
            System.out.println(explorer.findIdOfLeastLikedVideo());
            System.out.println(explorer.findIdOfMostLikedLeastDislikedVideo());
            System.out.println(explorer.findDistinctTitlesOfTop3VideosByViews());
            System.out.println(explorer.findIdOfMostTaggedVideo());
            System.out.println(explorer.findTitleOfFirstVideoTrendingBefore100KViews());
            System.out.println(explorer.findIdOfMostTrendingVideo());
        } catch (IOException e) {
            System.out.println("Error while reading from file.");
        }
        //System.out.println(file.getAbsolutePath());
    }


}
