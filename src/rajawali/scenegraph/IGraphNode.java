package rajawali.scenegraph;

import java.util.Collection;

import rajawali.Camera;
import rajawali.bounds.IBoundingVolume;
import rajawali.math.Number3D;
import rajawali.scene.RajawaliScene;


/**
 * Generic interface allowing for the incorporation of scene graphs
 * to the rendering pipeline of Rajawali. To be a member of scene graphs
 * which implement this interface, an object must inherit from 
 * ATransformable3D. 
 * 
 * @author Jared Woolston (jwoolston@tenkiv.com)
 */
public interface IGraphNode {
	
	/**
	 * This enum defines the different scene graphs which {@link RajawaliScene}
	 * can use. If a new type is created it should be added to this list. 
	 */
	public enum GRAPH_TYPE {
		NONE, OCTREE
	}
	
	/**
	 * Adds an object to the scene graph. Implementations do not
	 * need to support online adjustment of the scene graph, and
	 * should clearly document what their add behavior is.
	 * 
	 * @param object BaseObject3D to be added to the graph.
	 */
	public void addObject(IGraphNodeMember object);
	
	/**
	 * Adds a collection of objects to the scene graph. Implementations
	 * do not need to support online adustment of the scene graph, and
	 * should clearly document what their add behavior is.
	 * 
	 * @param objects Collection of {@link IGraphNodeMember} objects to add.
	 */
	public void addObjects(Collection<IGraphNodeMember> objects);
	
	/**
	 * Removes an object from the scene graph. Implementations do not
	 * need to support online adjustment of the scene graph, and should
	 * clearly document what their removal behavior is. 
	 * 
	 * @param object BaseObject3D to be removed from the graph.
	 */
	public void removeObject(IGraphNodeMember object);
	
	/**
	 * Removes a collection of objects from the scene graph. Implementations do not
	 * need to support online adjustment of the scene graph, and should
	 * clearly document what thier removal behavior is.
	 * 
	 * @param objects Collection of {@link IGraphNodeMember} objects to remove.
	 */
	public void removeObjects(Collection<IGraphNodeMember> objects);
	
	/**
	 * This should be called whenever an object has moved in the scene.
	 * Implementations should determine its new position in the graph.
	 * 
	 * @param object BaseObject3D to re-examine.
	 */
	public void updateObject(IGraphNodeMember object);
	
	/**
	 * Set the child addition behavior. Implementations are expected
	 * to document their default behavior.
	 * 
	 * @param recursive boolean Should the children be added recursively.
	 */
	public void addChildrenRecursively(boolean recursive);
	
	/**
	 * Set the child removal behavior. Implementations are expected to
	 * document their default behavior.
	 * 
	 * @param recursive boolean Should the children be removed recursively.
	 */
	public void removeChildrenRecursively(boolean recursive);
	
	/**
	 * Can be called to force a reconstruction of the scene graph
	 * with all added children. This is useful if the scene graph
	 * does not support online modification.
	 */
	public void rebuild();
	
	/**
	 * Can be called to remove all objects from the scene graph.
	 */
	public void clear();
	
	/**
	 * Called to cause the scene graph to determine which objects are
	 * contained (even partially) by the provided volume. How this is 
	 * done is left to the implementation.
	 * 
	 * @param volume IBoundingVolume to test visibility against.
	 */
	public void cullFromBoundingVolume(IBoundingVolume volume);
	
	/**
	 * Call this in the renderer to cause the scene graph to be
	 * displayed. It is up to the implementation to determine 
	 * the best way to accomplish this (draw volumes, write text,
	 * log statements, etc.)
	 * 
	 * @param display boolean indicating if the graph is to be displayed.
	 */
	public void displayGraph(Camera camera, float[] projMatrix, float[] vMatrix);
	
	/**
	 * Retrieve the minimum bounds of this scene.
	 * 
	 * @return {@link Number3D} The components represent the minimum value in each axis.
	 */
	public Number3D getSceneMinBound();
	
	/**
	 * Retrieve the maximum bounds of this scene.
	 * 
	 * @return {@link Number3D} The components represent the maximum value in each axis.
	 */
	public Number3D getSceneMaxBound();
	
	/**
	 * Retrieve the number of objects this node is aware of. This count should
	 * be recursive, meaning each node should ask its children for a count and
	 * return the sum of that count.
	 * 
	 * @return int containing the object count.
	 */
	public int getObjectCount();
	
	/**
	 * Does this volume fully contain the input volume.
	 * 
	 * @param boundingVolume Volume to check containment of.
	 * @return boolean result of containment test.
	 */
	public boolean contains(IBoundingVolume boundingVolume);
	
	/**
	 * Is this volume fully contained by the input volume.
	 * 
	 * @param boundingVolume Volume to check containment by.
	 * @return boolean result of containment test.
	 */
	public boolean isContainedBy(IBoundingVolume boundingVolume);
}