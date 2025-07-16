import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import Scene3D from './components/Scene3D';
import Navigation from './components/Navigation';
import Dashboard from './components/Dashboard';
import JournalList from './components/JournalList';
import JournalForm from './components/JournalForm';
import Profile from './components/Profile';
import Login from './components/Login';
import Signup from './components/Signup';

function App() {
  const [currentView, setCurrentView] = useState('login');
  const [user, setUser] = useState(null);
  const [journals, setJournals] = useState([]);
  const [selectedJournal, setSelectedJournal] = useState(null);

  // Demo data for development
  const demoJournals = [
    {
      id: '1',
      title: 'My First Entry',
      content: 'Today was a beautiful day. I started working on my journal app and I\'m excited about the 3D interface!',
      date: new Date().toISOString(),
      sentiment: 'HAPPY'
    },
    {
      id: '2',
      title: 'Learning Three.js',
      content: 'Diving deep into 3D graphics with Three.js. The possibilities are endless!',
      date: new Date(Date.now() - 86400000).toISOString(),
      sentiment: 'HAPPY'
    }
  ];

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setUser({ username: 'demo' });
      setJournals(demoJournals);
      setCurrentView('dashboard');
    }
  }, []);

  const handleLogin = (userData) => {
    setUser(userData);
    setJournals(demoJournals);
    setCurrentView('dashboard');
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setUser(null);
    setJournals([]);
    setCurrentView('login');
  };

  const handleCreateJournal = (journalData) => {
    const newJournal = {
      id: Date.now().toString(),
      ...journalData,
      date: new Date().toISOString(),
      sentiment: 'HAPPY'
    };
    setJournals([newJournal, ...journals]);
    setCurrentView('journals');
  };

  const handleEditJournal = (journalData) => {
    const updatedJournals = journals.map(journal =>
      journal.id === selectedJournal.id
        ? { ...journal, ...journalData }
        : journal
    );
    setJournals(updatedJournals);
    setSelectedJournal(null);
    setCurrentView('journals');
  };

  const handleDeleteJournal = (journalId) => {
    setJournals(journals.filter(journal => journal.id !== journalId));
  };

  const renderCurrentView = () => {
    switch (currentView) {
      case 'login':
        return <Login onLogin={handleLogin} onSwitchToSignup={() => setCurrentView('signup')} />;
      case 'signup':
        return <Signup onSignup={handleLogin} onSwitchToLogin={() => setCurrentView('login')} />;
      case 'dashboard':
        return <Dashboard journals={journals} onNavigate={setCurrentView} />;
      case 'journals':
        return (
          <JournalList
            journals={journals}
            onEdit={(journal) => {
              setSelectedJournal(journal);
              setCurrentView('create');
            }}
            onDelete={handleDeleteJournal}
          />
        );
      case 'create':
        return (
          <JournalForm
            journal={selectedJournal}
            onSave={selectedJournal ? handleEditJournal : handleCreateJournal}
            onCancel={() => {
              setSelectedJournal(null);
              setCurrentView('journals');
            }}
          />
        );
      case 'profile':
        return <Profile user={user} />;
      default:
        return <Dashboard journals={journals} onNavigate={setCurrentView} />;
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-900 via-blue-900 to-indigo-900 relative overflow-hidden">
      {/* 3D Background Scene */}
      <div className="fixed inset-0 z-0">
        <Scene3D />
      </div>

      {/* Main Content */}
      <div className="relative z-10 min-h-screen">
        {user && (
          <Navigation
            currentView={currentView}
            onNavigate={setCurrentView}
            onLogout={handleLogout}
            user={user}
          />
        )}

        <main className="container mx-auto px-4 py-8">
          <AnimatePresence mode="wait">
            <motion.div
              key={currentView}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0, y: -20 }}
              transition={{ duration: 0.3 }}
            >
              {renderCurrentView()}
            </motion.div>
          </AnimatePresence>
        </main>
      </div>
    </div>
  );
}

export default App;