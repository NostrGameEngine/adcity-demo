package org.ngengine.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.teavm.classlib.ReflectionContext;
import org.teavm.classlib.ReflectionSupplier;
import org.teavm.model.ClassReader;
import org.teavm.model.FieldReader;
 
import org.teavm.model.MethodDescriptor;
import org.teavm.model.MethodReader;
import org.teavm.model.ValueType;

public class TeaReflectionSupplier implements ReflectionSupplier {
    private static ArrayList<Pattern> regexPatterns = new ArrayList<>();

    private static ArrayList<Class<?>> clazzList = new ArrayList<>();
    private static ArrayList<String> clazzNameList = new ArrayList<>();
    static {
        init();
    }

    private static void initGenerated() {
        TeaReflectionSupplier.addReflectionClass("com.jcraft.jzlib.Deflate");
        TeaReflectionSupplier.addReflectionClass("com.jcraft.jzlib.GZIPHeader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.AnimClip");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.AnimComposer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.AnimLayer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.AnimTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.Armature");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.ArmatureMask");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.Joint");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.MatrixJointModelTransform");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.MorphControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.MorphTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.SeparateJointModelTransform");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.SingleLayerInfluenceMask");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.SkinningControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.TransformTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.tween.AbstractTween");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.tween.Tween");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.tween.action.Action");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.tween.action.BaseAction");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.tween.action.BlendAction");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.tween.action.BlendableAction");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.tween.action.ClipAction");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.util.HasLocalTransform");
        TeaReflectionSupplier.addReflectionClass("com.jme3.anim.util.JointModelTransform");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.AnimControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.Animation");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.AudioTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.Bone");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.BoneTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.ClonableTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.CompactArray");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.CompactFloatArray");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.CompactQuaternionArray");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.CompactVector3Array");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.EffectTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.Pose");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.PoseTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.Skeleton");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.SkeletonControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.SpatialTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.Track");
        TeaReflectionSupplier.addReflectionClass("com.jme3.animation.TrackInfo");
        TeaReflectionSupplier.addReflectionClass("com.jme3.app.StatsView");
        TeaReflectionSupplier.addReflectionClass("com.jme3.app.state.ScreenshotAppState");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.AssetKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.AssetLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.AssetLocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.AssetProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.CloneableAssetProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.CloneableSmartAsset");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.FilterKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.MaterialKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.ModelKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.ShaderNodeDefinitionKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.TextureKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.cache.AssetCache");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.cache.SimpleAssetCache");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.cache.WeakRefAssetCache");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.cache.WeakRefCloneAssetCache");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.plugins.ClasspathLocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.plugins.FileLocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.plugins.HttpZipLocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.plugins.UrlLocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.asset.plugins.ZipLocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.AudioBuffer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.AudioData");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.AudioKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.AudioNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.AudioStream");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.BandPassFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.Filter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.HighPassFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.LowPassFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.plugins.OGGLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.audio.plugins.WAVLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bounding.BoundingBox");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bounding.BoundingSphere");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bounding.BoundingVolume");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.animation.BoneLink");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.animation.DacConfiguration");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.animation.DacLinks");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.animation.DynamicAnimControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.animation.PhysicsLink");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.animation.RangeOfMotion");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.animation.TorsoLink");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.PhysicsCollisionObject");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.BoxCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.CapsuleCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.CollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.CompoundCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.ConeCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.CylinderCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.GImpactCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.HeightfieldCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.HullCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.MeshCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.PlaneCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.SimplexCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.SphereCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.collision.shapes.infos.ChildCollisionShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.control.AbstractPhysicsControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.control.BetterCharacterControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.control.CharacterControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.control.GhostControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.control.KinematicRagdollControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.control.PhysicsControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.control.RigidBodyControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.control.VehicleControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.debug.AbstractPhysicsDebugControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.debug.BulletCharacterDebugControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.debug.BulletGhostObjectDebugControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.debug.BulletJointDebugControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.debug.BulletRigidBodyDebugControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.debug.BulletVehicleDebugControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.joints.ConeJoint");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.joints.HingeJoint");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.joints.PhysicsJoint");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.joints.Point2PointJoint");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.joints.SixDofJoint");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.joints.SliderJoint");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.objects.PhysicsCharacter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.objects.PhysicsGhostObject");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.objects.PhysicsRigidBody");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.objects.PhysicsVehicle");
        TeaReflectionSupplier.addReflectionClass("com.jme3.bullet.objects.VehicleWheel");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.Cinematic");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.KeyFrame");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.MotionPath");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.TimeLine");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.AbstractCinematicEvent");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.AnimEvent");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.AnimationEvent");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.AnimationTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.CameraEvent");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.CinematicEvent");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.MotionEvent");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.MotionTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.SoundEvent");
        TeaReflectionSupplier.addReflectionClass("com.jme3.cinematic.events.SoundTrack");
        TeaReflectionSupplier.addReflectionClass("com.jme3.collision.bih.BIHNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.collision.bih.BIHTree");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.ParticleEmitter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.ParticleMesh");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.ParticlePointMesh");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.ParticleTriMesh");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.influencers.DefaultParticleInfluencer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.influencers.EmptyParticleInfluencer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.influencers.NewtonianParticleInfluencer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.influencers.ParticleInfluencer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.influencers.RadialParticleInfluencer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.shapes.EmitterBoxShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.shapes.EmitterMeshConvexHullShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.shapes.EmitterMeshFaceShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.shapes.EmitterMeshVertexShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.shapes.EmitterPointShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.shapes.EmitterShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.effect.shapes.EmitterSphereShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.environment.EnvironmentProbeControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.environment.util.BoundingSphereDebug");
        TeaReflectionSupplier.addReflectionClass("com.jme3.environment.util.Circle");
        TeaReflectionSupplier.addReflectionClass("com.jme3.export.JmeImporter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.export.NullSavable");
        TeaReflectionSupplier.addReflectionClass("com.jme3.export.Savable");
        TeaReflectionSupplier.addReflectionClass("com.jme3.export.binary.BinaryImporter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.export.binary.BinaryLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.export.xml.XMLImporter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.BitmapCharacter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.BitmapCharacterSet");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.BitmapFont");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.BitmapText");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.BitmapTextPage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.GlyphParser");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.Kerning");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.Rectangle");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.StringBlock");
        TeaReflectionSupplier.addReflectionClass("com.jme3.font.plugins.BitmapFontLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.input.ChaseCamera");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.AmbientLight");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.DirectionalLight");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.Light");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.LightList");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.LightProbe");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.OrientedBoxProbeArea");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.PointLight");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.ProbeArea");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.SphereProbeArea");
        TeaReflectionSupplier.addReflectionClass("com.jme3.light.SpotLight");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.MatParam");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.MatParamOverride");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.MatParamTexture");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.Material");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.MaterialList");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.MaterialProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.RenderState");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.ShaderGenerationInfo");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.Technique");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.TechniqueDef");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.logic.DefaultTechniqueDefLogic");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.logic.MultiPassLightingLogic");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.logic.SinglePassAndImageBasedLightingLogic");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.logic.SinglePassLightingLogic");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.logic.StaticPassLightingLogic");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.logic.TechniqueDefLogic");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.plugins.J3MLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.material.plugins.ShaderNodeDefinitionLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.ColorRGBA");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Line");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.LineSegment");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Matrix3f");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Matrix4f");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Plane");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Quaternion");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Ray");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Rectangle");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Ring");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Spline");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Transform");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Triangle");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Vector2f");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Vector3f");
        TeaReflectionSupplier.addReflectionClass("com.jme3.math.Vector4f");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.AbstractMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.Message");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.message.ChannelInfoMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.message.ClientRegistrationMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.message.CompressedMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.message.DisconnectMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.message.GZIPCompressedMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.message.SerializerRegistrationsMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.message.ZIPCompressedMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.rmi.RemoteMethodCallMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.rmi.RemoteMethodReturnMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.rmi.RemoteObjectDefMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.service.rpc.msg.RpcCallMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.network.service.rpc.msg.RpcResponseMessage");
        TeaReflectionSupplier.addReflectionClass("com.jme3.plugins.gson.GsonParser");
        TeaReflectionSupplier.addReflectionClass("com.jme3.plugins.json.JsonParser");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.Filter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.FilterPostProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.HDRRenderer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.PreDepthProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.SceneProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.BloomFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.CartoonEdgeFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.ColorOverlayFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.ComposeFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.ContrastAdjustmentFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.CrossHatchFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.DepthOfFieldFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.FXAAFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.FadeFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.FogFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.GammaCorrectionFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.KHRToneMapFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.LightScatteringFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.PosterizationFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.RadialBlurFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.SoftBloomFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.ToneMapFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.filters.TranslucentBucketFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.post.ssao.SSAOFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.renderer.Camera");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.AssetLinkNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.BatchNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.CameraNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.CollisionData");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.Geometry");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.GeometryGroupNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.LightNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.Mesh");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.Node");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.SimpleBatchNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.Spatial");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.UserData");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.VertexBuffer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.control.AbstractControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.control.BillboardControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.control.CameraControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.control.Control");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.control.LightControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.control.LodControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.control.UpdateControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.Arrow");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.Grid");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.SkeletonDebugger");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.SkeletonInterBoneWire");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.SkeletonPoints");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.SkeletonWire");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.WireBox");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.WireFrustum");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.WireSphere");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.custom.ArmatureDebugger");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.custom.ArmatureInterJointsWire");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.custom.ArmatureNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.debug.custom.JointShape");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.instancing.InstancedGeometry");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.instancing.InstancedNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.mesh.MorphTarget");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.IrBoneWeightIndex");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.IrVertex");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.MTLLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.OBJLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.fbx.ContentTextureKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.fbx.ContentTextureLocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.fbx.FbxLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.fbx.SceneKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.fbx.SceneLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.fbx.SceneWithAnimationLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.gltf.BinDataKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.gltf.BinLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.gltf.GlbLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.gltf.GltfLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.gltf.GltfModelKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.ogre.MaterialLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.ogre.MeshLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.ogre.OgreMeshKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.ogre.SceneLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.ogre.SceneMeshLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.ogre.SkeletonLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.plugins.ogre.matext.OgreMaterialKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.AbstractBox");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Box");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.CenterQuad");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Curve");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Cylinder");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Dome");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Line");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.PQTorus");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Quad");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.RectangleMesh");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Sphere");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.StripBox");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Surface");
        TeaReflectionSupplier.addReflectionClass("com.jme3.scene.shape.Torus");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.Shader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.ShaderNode");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.ShaderNodeDefinition");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.ShaderNodeVariable");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.VariableMapping");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.bufferobject.BufferObject");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.bufferobject.BufferRegion");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.plugins.GLSLLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shader.plugins.ShaderAssetKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.AbstractShadowFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.AbstractShadowRenderer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.BasicShadowRenderer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.DirectionalLightShadowFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.DirectionalLightShadowRenderer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.PointLightShadowFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.PointLightShadowRenderer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.PssmShadowFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.PssmShadowRenderer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.SpotLightShadowFilter");
        TeaReflectionSupplier.addReflectionClass("com.jme3.shadow.SpotLightShadowRenderer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.system.AppSettings");
        TeaReflectionSupplier.addReflectionClass("com.jme3.system.JmeSystem");
        TeaReflectionSupplier.addReflectionClass("com.jme3.system.JmeSystemDelegate");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.GeoMap");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.LODGeomap");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.MultiTerrainLodControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.NormalRecalcControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.TerrainGrid");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.TerrainGridLodControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.TerrainGridTileLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.TerrainLodControl");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.TerrainPatch");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.TerrainQuad");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.grid.AssetTileLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.grid.FractalTileLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.grid.ImageTileLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.lodcalc.LodCalculator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.lodcalc.LodThreshold");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.lodcalc.PerspectiveLodCalculator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.terrain.geomipmap.lodcalc.SimpleLodThreshold");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.FrameBuffer");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.Image");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.Texture");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.Texture2D");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.Texture3D");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.TextureArray");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.TextureCubeMap");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.TextureProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.plugins.DDSLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.plugins.PFMLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.plugins.SVGTextureKey");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.plugins.TGALoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.texture.plugins.ktx.KTXLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.ui.Picture");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.BufferAllocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.IntMap");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.ListMap");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.NativeObject");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.PrimitiveAllocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.ReflectionAllocator");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.SafeArrayList");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.clone.JmeCloneable");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.res.DefaultResourceLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.res.ResourceLoader");
        TeaReflectionSupplier.addReflectionClass("com.jme3.util.struct.StructStd140BufferObject");
        TeaReflectionSupplier.addReflectionClass("com.jme3.water.ReflectionProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.water.SimpleWaterProcessor");
        TeaReflectionSupplier.addReflectionClass("com.jme3.water.WaterFilter");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.ActionButton");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Button");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Checkbox");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Container");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.GridPanel");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Insets3f");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Label");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.ListBox");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.OptionPanel");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Panel");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.PasswordField");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.ProgressBar");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.RollupPanel");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Selector");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Slider");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.Spinner");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.TabbedPanel");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.TextField");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.AbstractGuiComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.BorderLayout");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.BoxLayout");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.DynamicInsetsComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.IconComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.InsetsComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.QuadBackgroundComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.SpringGridLayout");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.TbtQuadBackgroundComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.Text2d");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.TextComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.component.TextEntryComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.core.AbstractNodeControl");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.core.CommandMap");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.core.GuiComponent");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.core.GuiControl");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.core.GuiLayout");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.core.GuiMaterial");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.core.LightingMaterialAdapter");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.core.UnshadedMaterialAdapter");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.dnd.DragAndDropControl");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.effect.EffectControl");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.event.CursorEventControl");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.event.MouseEventControl");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.focus.DefaultFocusTraversalControl");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.geom.DMesh");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.geom.MBox");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.geom.TbtQuad");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.list.DefaultCellRenderer");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.style.Attributes");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.style.ContainsSelector");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.style.ElementId");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.style.ElementSelector");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.style.Selector");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.style.StyleTree");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.style.Styles");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.text.DefaultDocumentModel");
        TeaReflectionSupplier.addReflectionClass("com.simsilica.lemur.value.DefaultValueRenderer");
        TeaReflectionSupplier.addReflectionClass("de.jarnbjo.vorbis.Floor1");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.ads.ImmersiveAdComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.ads.ImmersiveAdControl");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.Auth");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.AuthConfig");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.AuthSelectionWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.AuthStrategy");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.Nip46AuthStrategy");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.nip07.Nip07Auth");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.nip07.Nip07AuthWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.nip46.Nip46Auth");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.nip46.Nip46AuthWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.nip46.Nip46ChallengeWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.nsec.NsecAuth");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.nsec.NsecAuthWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.auth.stored.StoredAuthSelectionWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.Component");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.StallingComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.AppFragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.AssetLoadingFragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.AsyncAssetLoadingFragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.Fragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.GuiViewPortFragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.InputHandlerFragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.LogicFragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.MainViewPortFragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.components.fragments.RenderFragment");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.demo.adc.CharacterComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.demo.adc.HudComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.demo.adc.LoadingScreenComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.demo.adc.MapComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.demo.adc.PhysicsComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.demo.adc.PostprocessingComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.demo.adc.SoundComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.CollectionSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.MapSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.Nip46MetadataSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.Nip46SignerSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.NostrEventSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.NostrKeyPairSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.NostrPrivateKeySavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.NostrPublicKeySavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.NostrRelayInfoSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.NumberSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.SavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.export.StringSavableWrapper");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NAspectPreservingQuadBackground");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NButton");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NIconButton");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NLabel");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NLoadingSpinner");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NQrViewer");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NSVGIcon");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NTextInput");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.NVSpacer");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.containers.NColumn");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.containers.NContainer");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.containers.NMultiPageList");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.containers.NPanel");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.components.containers.NRow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.qr.BitBuffer");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.win.NToast");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.win.NWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.win.NWindowManagerComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.win.std.NConfirmDialogWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.win.std.NErrorWindow");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.gui.win.std.NHud");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.lnurl.LnUrlPayerData");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.network.Lobby");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.network.LocalLobby");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.network.protocol.messages.BinaryMessage");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.network.protocol.messages.ByteDataMessage");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.network.protocol.messages.ClassRegistrationAckMessage");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.network.protocol.messages.CompressedMessage");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.network.protocol.messages.TextDataMessage");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.network.protocol.messages.TextMessage");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.NostrFilter");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.NostrRelayInfo");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.NostrRelayLifecycleManager");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.NostrRelaySubManager");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.event.NostrEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.event.SignedNostrEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.event.UnsignedNostrEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.keypair.NostrKey");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.keypair.NostrKeyPair");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.keypair.NostrPrivateKey");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.keypair.NostrPublicKey");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.listeners.NostrRelayComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.nip01.Nip01UserMetadataFilter");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.nip05.Nip05Nip46Data");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.nip46.BunkerUrl");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.nip46.Nip46AppMetadata");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.nip46.NostrconnectUrl");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.nip50.NostrSearchFilter");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.rtc.turn.NostrTURNSettings");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.signer.NostrKeyPairSigner");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.signer.NostrNIP07Signer");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.signer.NostrNIP46Signer");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostr4j.signer.NostrSigner");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.AdBidEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.AdBidFilter");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.AdEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.negotiation.AdAcceptOfferEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.negotiation.AdBailEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.negotiation.AdNegotiationEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.negotiation.AdOfferEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.negotiation.AdPaymentRequestEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.negotiation.AdPayoutEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.nostrads.protocol.negotiation.AdPowNegotiationEvent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.platform.RTCSettings");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.player.PlayerManagerComponent");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.web.context.HeapAllocator");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.web.context.WebSystem");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.web.filesystem.WebImageLoader");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.web.filesystem.WebLocator");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.web.filesystem.WebResourceLoader");
        TeaReflectionSupplier.addReflectionClass("org.ngengine.web.json.TeaJSONParser");
    }

    private static void init() {
        initGenerated();
    }

    public static void addReflectionClass(Class<?> c) {
        clazzList.add(c);
    }

    public static void addReflectionClass(String className) {
        clazzNameList.add(className);
    }

 
    public static void addRegex(String regex) {
        regexPatterns.add(Pattern.compile(regex));
    }
    public TeaReflectionSupplier() {
        System.out.println("TeaReflectionSupplier initialized");

    }

    @Override
    public Collection<String> getAccessibleFields(ReflectionContext context, String className) {
        Set<String> fields = new HashSet<>();
        ClassReader cls = context.getClassSource().get(className);
        if (cls == null) {
            return Collections.emptyList();
        }
        try {

            Class<?> clazz = context.getClassLoader().loadClass(className);

            if (cls != null) {
                if (canHaveReflection(clazz)) {
                    for (FieldReader field : cls.getFields()) {
                        String name = field.getName();
                        fields.add(name);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            new RuntimeException(e);
        }

        return fields;
    }

    private boolean isWhitelistedType(ReflectionContext context, ValueType t) throws ClassNotFoundException{
        boolean canHaveReflection = false;        
        if (t instanceof ValueType.Void) {
            return true;
        }
        if (t instanceof ValueType.Array) {
            ValueType.Array a = (ValueType.Array) t;
            t = a.getItemType();

        }
        if (t instanceof ValueType.Object) {
            ValueType.Object obj = (ValueType.Object) t;
            if(obj.getClassName().startsWith("java.lang")) {
                canHaveReflection = true;
            } else {
                Class<?> sc = context.getClassLoader().loadClass(obj.getClassName());
                canHaveReflection = canHaveReflection(sc);
            }
        } else if (t instanceof ValueType.Primitive) {
            canHaveReflection = true;
        }
        return canHaveReflection;

    }

    @Override
    public Collection<MethodDescriptor> getAccessibleMethods(ReflectionContext context, String className) {
        Set<MethodDescriptor> methods = new HashSet<>();

        ClassReader cls = context.getClassSource().get(className);
        if (cls == null) {
            return Collections.emptyList();
        }
        try {
            Class<?> clazz = context.getClassLoader().loadClass(className);

            if (canHaveReflection(clazz)) {
                Collection<? extends MethodReader> methods2 = cls.getMethods();
                int constructors = 0;
                for (MethodReader method : methods2) {      
                    
                    boolean isConstructor = method.getName().equals("<init>");
                    if(isConstructor) {
                        constructors++;
                    }
       
                    boolean canHaveReflection = true;     
                 
                    
                    if (canHaveReflection && !isConstructor) {
                        ValueType t = method.getResultType();
                        canHaveReflection = isWhitelistedType(context, t);
                    }
                    
                    if (!canHaveReflection) {
                        continue;
                    }

                    MethodDescriptor descriptor = method.getDescriptor();
                    methods.add(descriptor);
                }
            }
        } catch (ClassNotFoundException e) {
            new RuntimeException(e);
        }
        return methods;
    }

    private boolean canHaveReflection(Class<?> clazz) {
        for (Class<?> c : clazzList) {

            if (c.isAssignableFrom(clazz) || c.equals(clazz)) {
                return true;
            }
        }
        String className = clazz.getName();
        for(String cn : clazzNameList) {
            if(cn.equals(className)) {
                return true;
            }
        }
        for (Pattern pattern : regexPatterns) {
            if (pattern.matcher(className).matches()) {
                return true;
            }
        }

        return false;
    }
}