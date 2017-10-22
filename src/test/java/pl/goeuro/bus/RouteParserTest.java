package pl.goeuro.bus;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.util.Map;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

public class RouteParserTest {


	@Test
	public void shouldParseRouteFile() {
		//given
		RouteFileParser routeFileParser = new RouteFileParser("src/test/resources/routes_small");

		//when
		Map<Integer, TreeSet<Integer>> routes = routeFileParser.parse();

		//then
		assertThat(routes.isEmpty()).isFalse();
	}


	@Test
	public void shouldParseMediumRouteFile() {
		//given
		RouteFileParser routeFileParser = new RouteFileParser("src/test/resources/routes_medium");

		//when
		Map<Integer, TreeSet<Integer>> routes = routeFileParser.parse();

		//then
		assertThat(routes.size()).isEqualTo(7);
		assertThat(routes.get(0)).isEqualTo(Sets.newSet(0,2));
		assertThat(routes.get(1)).isEqualTo(Sets.newSet(0,1));
		assertThat(routes.get(2)).isEqualTo(Sets.newSet(0));
		assertThat(routes.get(3)).isEqualTo(Sets.newSet(0,1));
		assertThat(routes.get(4)).isEqualTo(Sets.newSet(0,2));
		assertThat(routes.get(5)).isEqualTo(Sets.newSet(1));
		assertThat(routes.get(6)).isEqualTo(Sets.newSet(1,2));
	}



}
