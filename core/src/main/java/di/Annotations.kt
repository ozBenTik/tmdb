package di
import javax.inject.Qualifier


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class PopularMovies

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class TopRatedMovies

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class UpcomingMovies

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class NowPlayingMovies

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class Discovery

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class ApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class OnAirTvShows

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class AiringTodayTvShows


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class PopularTvShows

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class TopRatedTvShows