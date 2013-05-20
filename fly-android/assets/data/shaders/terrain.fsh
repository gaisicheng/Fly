precision mediump float;       // Set the default precision to medium. We don't need as high of a
                               // precision in the fragment shader.
uniform vec3 u_lightPos;       // The position of the light in eye space.
uniform vec4 u_tint;           // The overlaying color.
uniform vec4 diffuseColor;           // The overlaying color.

varying vec3 v_position;       // Interpolated position for this fragment.
                               // triangle per fragment.
varying vec3 v_normal;         // Interpolated normal for this fragment.

// The entry point for our fragment shader.
void main()
{
    // Will be used for attenuation.
    float distance = length(u_lightPos - v_position.xyz);

    // Get a lighting direction vector from the light to the vertex.
    vec3 lightVector = normalize(u_lightPos - v_position.xyz);

    // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
      // pointing in the same direction then it will get max illumination.
      float diffuse = dot(v_normal, lightVector);

      // Add attenuation.


      if(diffuse > 0.5){
    gl_FragColor = diffuseColor * diffuse ;
      }

}
