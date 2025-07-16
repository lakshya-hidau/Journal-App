import React, { useRef, useMemo } from 'react';
import { Canvas, useFrame } from '@react-three/fiber';
import { OrbitControls, Stars, Text, Box } from '@react-three/drei';
import * as THREE from 'three';

function FloatingBook({ position, rotation, color }) {
  const meshRef = useRef();
  
  useFrame((state) => {
    if (meshRef.current) {
      meshRef.current.rotation.y += 0.01;
      meshRef.current.position.y += Math.sin(state.clock.elapsedTime + position[0]) * 0.002;
    }
  });

  return (
    <group ref={meshRef} position={position} rotation={rotation}>
      <Box args={[0.8, 1.2, 0.1]} position={[0, 0, 0]}>
        <meshStandardMaterial color={color} />
      </Box>
      <Box args={[0.7, 1.1, 0.05]} position={[0, 0, 0.08]}>
        <meshStandardMaterial color="#ffffff" />
      </Box>
    </group>
  );
}

function AnimatedSphere({ position, color, speed = 1 }) {
  const meshRef = useRef();
  
  useFrame((state) => {
    if (meshRef.current) {
      meshRef.current.rotation.x += 0.01 * speed;
      meshRef.current.rotation.y += 0.01 * speed;
      meshRef.current.position.y += Math.sin(state.clock.elapsedTime * speed) * 0.001;
    }
  });

  return (
    <mesh ref={meshRef} position={position}>
      <sphereGeometry args={[0.3, 32, 32]} />
      <meshStandardMaterial color={color} transparent opacity={0.7} />
    </mesh>
  );
}

function Scene3DContent() {
  const books = useMemo(() => [
    { position: [-4, 2, -5], rotation: [0, 0.5, 0], color: '#8B5CF6' },
    { position: [4, -1, -8], rotation: [0, -0.3, 0], color: '#06B6D4' },
    { position: [-2, -3, -6], rotation: [0, 1, 0], color: '#F59E0B' },
    { position: [3, 3, -7], rotation: [0, -0.8, 0], color: '#EF4444' },
    { position: [0, 1, -10], rotation: [0, 0, 0], color: '#10B981' },
  ], []);

  const spheres = useMemo(() => [
    { position: [-6, 0, -3], color: '#8B5CF6', speed: 0.8 },
    { position: [6, 2, -4], color: '#06B6D4', speed: 1.2 },
    { position: [0, -4, -2], color: '#F59E0B', speed: 0.6 },
    { position: [-3, 4, -5], color: '#EF4444', speed: 1.5 },
  ], []);

  return (
    <>
      <ambientLight intensity={0.4} />
      <pointLight position={[10, 10, 10]} intensity={1} />
      <pointLight position={[-10, -10, -10]} intensity={0.5} color="#8B5CF6" />
      
      <Stars radius={100} depth={50} count={5000} factor={4} saturation={0} fade speed={1} />
      
      {books.map((book, index) => (
        <FloatingBook key={index} {...book} />
      ))}
      
      {spheres.map((sphere, index) => (
        <AnimatedSphere key={index} {...sphere} />
      ))}
      
      <OrbitControls
        enableZoom={false}
        enablePan={false}
        autoRotate
        autoRotateSpeed={0.5}
        maxPolarAngle={Math.PI / 2}
        minPolarAngle={Math.PI / 3}
      />
    </>
  );
}

export default function Scene3D() {
  return (
    <Canvas
      camera={{ position: [0, 0, 5], fov: 75 }}
      style={{ background: 'transparent' }}
    >
      <Scene3DContent />
    </Canvas>
  );
}