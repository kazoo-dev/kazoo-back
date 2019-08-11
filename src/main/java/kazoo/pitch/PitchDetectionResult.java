package kazoo.pitch;

public class PitchDetectionResult {
    /**
     * The kazoo.pitch in Hertz.
     */
    private float pitch;

    private float probability;

    private boolean pitched;

    public PitchDetectionResult(){
        pitch = -1;
        probability = -1;
        pitched = false;
    }

    public PitchDetectionResult(PitchDetectionResult other){
        this.pitch = other.pitch;
        this.probability = other.probability;
        this.pitched = other.pitched;
    }


    /**
     * @return The kazoo.pitch in Hertz.
     */
    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public PitchDetectionResult clone(){
        return new PitchDetectionResult(this);
    }

    /**
     * @return A probability (noisiness, (a)periodicity, salience, voicedness or
     *         clarity measure) for the detected kazoo.pitch. This is somewhat similar
     *         to the term voiced which is used in speech recognition. This
     *         probability should be calculated together with the kazoo.pitch. The
     *         exact meaning of the value depends on the detector used.
     */
    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    /**
     * @return Whether the algorithm thinks the block of audio is pitched. Keep
     *         in mind that an algorithm can come up with a best guess for a
     *         kazoo.pitch even when isPitched() is false.
     */
    public boolean isPitched() {
        return pitched;
    }

    public void setPitched(boolean pitched) {
        this.pitched = pitched;
    }
}
