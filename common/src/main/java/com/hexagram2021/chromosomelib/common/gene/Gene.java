package com.hexagram2021.chromosomelib.common.gene;

import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.hexagram2021.chromosomelib.common.gene_locus.GeneLocus;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;

/**
 * A gene is a unit of hereditary information.
 */
@SuppressWarnings("UnstableApiUsage")
public class Gene {
	/**
	 * The relationship between genes.
	 * <p> Assert: this graph is a directed acyclic graph.
	 */
	private static ImmutableGraph<Holder<Gene>> disableRelationship = GraphBuilder.directed().<Holder<Gene>>immutable().build();

	public static final Comparator<Holder<Gene>> COMPARATOR = Comparator
			.<Holder<Gene>>comparingInt(holder -> holder.value().topologicalOrder)
			.thenComparing(holder -> holder.value().code());

	/**
	 * The locus of this gene.
	 */
	@ApiStatus.Internal
	@Nullable
	public Holder<GeneLocus> geneLocus = null;

	/**
	 * @see com.hexagram2021.chromosomelib.registry.RegistryRelations#buildGeneTopologicalOrder
	 */
	@ApiStatus.Internal
	public int topologicalOrder = -1;

	private final String code;

	public Gene(String code) {
		this.code = code;
	}

	/**
	 * Get the code of this gene.
	 * @return code
	 */
	public String code() {
		return this.code;
	}

	/**
	 * Use Breadth-First Search to check whether two genes are connected or not.
	 *
	 * @see com.hexagram2021.chromosomelib.common.util.Mappers#convertChromosomeInstancesToExpressingGenes(Collection)
	 * @see Gene#doDisable(Set)
	 * @deprecated I don't think you need this lol.
	 * @param gene1	the first gene, probably the dominant gene
	 * @param gene2	the second gene, probably the recessive gene
	 * @return {@code true} if two genes are connected, {@code false} otherwise
	 */
	@Deprecated
	public static boolean isConnected(Holder<Gene> gene1, Holder<Gene> gene2) {
		Queue<Holder<Gene>> queue = Queues.newArrayDeque();
		queue.add(gene1);
		while(!queue.isEmpty()) {
			Holder<Gene> gene = queue.poll();
			for(Holder<Gene> neighbor : disableRelationship.successors(gene)) {
				if(neighbor == gene2) {
					return true;
				}
				queue.add(neighbor);
			}
		}
		return false;
	}

	/**
	 * Do NOT call this method while iterating over the set.
	 * <p><b>Idempotency</b>: for any legal set {@code s}, after calling the first {@code doDisable(s)}, any call to {@code doDisable(s)} will have no effect to set {@code s}.
	 * @param map	the map of all genes, which may contains some genes to be disabled.
	 */
	@ApiStatus.Internal
	public static void doDisable(Object2IntMap<Holder<Gene>> map) {
		Queue<Holder<Gene>> queue = Queues.newArrayDeque();
		queue.addAll(map.keySet());
		Set<Holder<Gene>> visited = Sets.newIdentityHashSet();
		while(!queue.isEmpty()) {
			Holder<Gene> gene = queue.poll();
			for(Holder<Gene> neighbor : disableRelationship.successors(gene)) {
				if(!visited.contains(neighbor)) {
					visited.add(neighbor);
					queue.add(neighbor);
					map.removeInt(neighbor);
				}
			}
		}
	}

	@ApiStatus.Internal
	public static void setDisableRelationship(ImmutableGraph<Holder<Gene>> disableRelationship) {
		Gene.disableRelationship = disableRelationship;
	}
}
