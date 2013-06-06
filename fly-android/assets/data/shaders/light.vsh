uniform mat4 u_MVPMatrix;      // A constant representing the combined model/view/projection matrix.
uniform mat4 u_MVMatrix;       // A constant representing the combined model/view matrix.
uniform vec3 u_lightPos;       // The position of the light in eye space.

attribute vec4 a_position;     // Per-vertex position information we will pass in.
attribute vec4 a_color;        // Per-vertex color information we will pass in.
attribute vec3 a_normal;       // Per-vertex normal information we will pass in.

varying float v_position;
varying float v_diffuse;

// The entry point for our vertex shader.
void main()
{
	    // Get a lighting direction vector from the light to the vertex.
	    vec3 lightVector = normalize(u_lightPos - a_position);

	    lightVector = vec3(-1,1,-1);
	    // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
	    // pointing in the same direction then it will get max illumination.
	    v_diffuse = max(dot(a_normal, lightVector), 0.7);
	    v_position = a_position;



    // gl_Position is a special variable used to store the final position.
    // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
    gl_Position = u_MVPMatrix * a_position;
}
